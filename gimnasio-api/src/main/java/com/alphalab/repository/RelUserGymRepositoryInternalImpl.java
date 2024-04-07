package com.alphalab.repository;

import com.alphalab.domain.RelUserGym;
import com.alphalab.repository.rowmapper.GymRowMapper;
import com.alphalab.repository.rowmapper.RelUserGymRowMapper;
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

/**
 * Spring Data R2DBC custom repository implementation for the RelUserGym entity.
 */
@SuppressWarnings("unused")
class RelUserGymRepositoryInternalImpl extends SimpleR2dbcRepository<RelUserGym, Long> implements RelUserGymRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final RelUserGymRowMapper relUserGymMapper;

    private final GymRowMapper gymRowMapper;

    private static final Table entityTable = Table.aliased("rel_user_gym", EntityManager.ENTITY_ALIAS);
    private static final Table userTable = Table.aliased("user", "user");
    private static final Table gymTable = Table.aliased("gym", "gym");

    public RelUserGymRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        RelUserGymRowMapper relUserGymMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        GymRowMapper gymRowMapper
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(RelUserGym.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.relUserGymMapper = relUserGymMapper;
        this.gymRowMapper = gymRowMapper;
    }

    @Override
    public Flux<RelUserGym> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<RelUserGym> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = RelUserGymSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(GymSqlHelper.getColumns(gymTable, "gym"));
        SelectBuilder.SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(gymTable)
            .on(Column.create("gym_id", entityTable))
            .equals(Column.create("id", gymTable))
            .leftOuterJoin(userTable)
            .on(Column.create("user_id", entityTable))
            .equals(Column.create("id", userTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, RelUserGym.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<RelUserGym> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<RelUserGym> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private RelUserGym process(Row row, RowMetadata metadata) {
        RelUserGym entity = relUserGymMapper.apply(row, "e");
        entity.setGym(gymRowMapper.apply(row, "gym"));
        return entity;
    }

    @Override
    public <S extends RelUserGym> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
