package com.alphalab.repository;

import com.alphalab.domain.Tournament;
import com.alphalab.repository.rowmapper.TournamentRowMapper;
import com.alphalab.repository.rowmapper.GameRowMapper;
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


@SuppressWarnings("unused")
class TournamentRepositoryInternalImpl extends SimpleR2dbcRepository<Tournament, Long> implements TournamentRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final TournamentRowMapper tournamentMapper;

    private final GameRowMapper gameRowMapper;

    private static final Table entityTable = Table.aliased("tournament", EntityManager.ENTITY_ALIAS);
    private static final Table gameTable = Table.aliased("game", "game");

    public TournamentRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        TournamentRowMapper tournamentMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        GameRowMapper gameRowMapper
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Tournament.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.tournamentMapper = tournamentMapper;
        this.gameRowMapper = gameRowMapper;
    }

    @Override
    public Flux<Tournament> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Tournament> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = TournamentSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(GameSqlHelper.getColumns(gameTable, "game"));
        SelectBuilder.SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(gameTable)
            .on(Column.create("gameid", entityTable))
            .equals(Column.create("id", gameTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Tournament.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Tournament> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Tournament> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Tournament process(Row row, RowMetadata metadata) {
        Tournament entity = tournamentMapper.apply(row, "e");
        entity.setGame(gameRowMapper.apply(row, "game"));
        return entity;
    }

    @Override
    public <S extends Tournament> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<Tournament> findAllTournamentsOfGame(Long gameid, Pageable pageable) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("gameid"), Conditions.just(gameid.toString()));
        return createQuery(pageable, whereClause).all();
    }

    @Override
    public Mono<Long> countFindAllTournamentsOfGame(Long gameid) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("gameid"), Conditions.just(gameid.toString()));
        return createQuery(null, whereClause).all().count();
    }

}
