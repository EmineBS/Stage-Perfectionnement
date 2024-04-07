package com.alphalab.repository;

import com.alphalab.domain.Criteria;
import com.alphalab.repository.rowmapper.CriteriaRowMapper;
import com.alphalab.repository.rowmapper.GymRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the Criteria entity.
 */
@SuppressWarnings("unused")
class CriteriaRepositoryInternalImpl extends SimpleR2dbcRepository<Criteria, Long> implements CriteriaRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final CriteriaRowMapper criteriaMapper;
    private final GymRowMapper gymRowMapper;

    private static final Table entityTable = Table.aliased("criteria", EntityManager.ENTITY_ALIAS);
    private static final Table gymTable = Table.aliased("gym", "gym");

    public CriteriaRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        CriteriaRowMapper criteriaMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        GymRowMapper gymRowMapper
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Criteria.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.criteriaMapper = criteriaMapper;
        this.gymRowMapper = gymRowMapper;
    }

    @Override
    public Flux<Criteria> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Criteria> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = CriteriaSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(GymSqlHelper.getColumns(gymTable, "gym"));
        SelectBuilder.SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(gymTable)
            .on(Column.create("gym_id", entityTable))
            .equals(Column.create("id", gymTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Criteria.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Criteria> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Criteria> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Criteria process(Row row, RowMetadata metadata) {
        Criteria entity = criteriaMapper.apply(row, "e");
        entity.setGym(gymRowMapper.apply(row, "gym"));
        return entity;
    }

    @Override
    public <S extends Criteria> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<Criteria> findByGymId(Pageable pageable, Long gym_id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("gym_id"), Conditions.just("'" + gym_id.toString() + "'"));
        return createQuery(pageable, whereClause).all();
    }

    @Override
    public Mono<Long> countByGymId(Long gym_id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("gym_id"), Conditions.just("'" + gym_id.toString() + "'"));
        return createQuery(null, whereClause).all().count();
    }

    @Override
    public Flux<Criteria> findAllOfGym(Long gym_id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("gym_id"), Conditions.just("'" + gym_id.toString() + "'"));
        return createQuery(null, whereClause).all();
    }
}
