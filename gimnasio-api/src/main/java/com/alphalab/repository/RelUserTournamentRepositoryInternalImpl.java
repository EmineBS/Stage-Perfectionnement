package com.alphalab.repository;

import com.alphalab.domain.RelUserTournament;
import com.alphalab.repository.rowmapper.TournamentRowMapper;
import com.alphalab.repository.rowmapper.RelUserTournamentRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.sql.*;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
class RelUserTournamentRepositoryInternalImpl extends SimpleR2dbcRepository<RelUserTournament, Long> implements RelUserTournamentRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final RelUserTournamentRowMapper relUserTournamentMapper;

    private final TournamentRowMapper tournamentRowMapper;

    private static final Table entityTable = Table.aliased("rel_user_tournament", EntityManager.ENTITY_ALIAS);
    private static final Table userTable = Table.aliased("jhi_user", "user");
    private static final Table tournamentTable = Table.aliased("tournament", "tournament");

    public RelUserTournamentRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        RelUserTournamentRowMapper relUserTournamentMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        TournamentRowMapper tournamentRowMapper
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(RelUserTournament.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.relUserTournamentMapper = relUserTournamentMapper;
        this.tournamentRowMapper = tournamentRowMapper;
    }

    @Override
    public Flux<RelUserTournament> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<RelUserTournament> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = RelUserTournamentSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(TournamentSqlHelper.getColumns(tournamentTable, "tournament"));
        //columns.addAll(UserSqlHelper.getColumns(userTable, "user"));
        SelectBuilder.SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(tournamentTable)
            .on(Column.create("tournament_id", entityTable))
            .equals(Column.create("id", tournamentTable));
            //.leftOuterJoin(userTable)
            //.on(Column.create("user_id", entityTable))
            //.equals(Column.create("id", userTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, RelUserTournament.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<RelUserTournament> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<RelUserTournament> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private RelUserTournament process(Row row, RowMetadata metadata) {
        RelUserTournament entity = relUserTournamentMapper.apply(row, "e");
        entity.setTournament(tournamentRowMapper.apply(row, "tournament"));
        return entity;
    }

    @Override
    public <S extends RelUserTournament> Mono<S> save(S entity) {
        return super.save(entity);
    }

}
