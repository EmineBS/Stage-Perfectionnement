package com.alphalab.repository;

import com.alphalab.domain.RelGymFeature;
import com.alphalab.repository.rowmapper.FeatureRowMapper;
import com.alphalab.repository.rowmapper.RelGymFeatureRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the RelGymFeature entity.
 */
@SuppressWarnings("unused")
class RelGymFeatureRepositoryInternalImpl extends SimpleR2dbcRepository<RelGymFeature, Long> implements RelGymFeatureRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final RelGymFeatureRowMapper relGymFeatureMapper;

    private final FeatureRowMapper featureRowMapper;

    private static final Table entityTable = Table.aliased("rel_gym_feature", EntityManager.ENTITY_ALIAS);
    private static final Table gymTable = Table.aliased("gym", "gym");
    private static final Table featureTable = Table.aliased("feature", "feature");

    public RelGymFeatureRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        RelGymFeatureRowMapper relGymFeatureMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        FeatureRowMapper featureRowMapper) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(RelGymFeature.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.relGymFeatureMapper = relGymFeatureMapper;
        this.featureRowMapper = featureRowMapper;
    }

    @Override
    public Flux<RelGymFeature> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<RelGymFeature> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = RelGymFeatureSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(FeatureSqlHelper.getColumns(featureTable, "feature"));
        SelectBuilder.SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(featureTable)
            .on(Column.create("feature_id", entityTable))
            .equals(Column.create("id", featureTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, RelGymFeature.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<RelGymFeature> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<RelGymFeature> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private RelGymFeature process(Row row, RowMetadata metadata) {
        RelGymFeature entity = relGymFeatureMapper.apply(row, "e");
        entity.setFeature(featureRowMapper.apply(row, "feature"));
        return entity;
    }

    @Override
    public <S extends RelGymFeature> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Mono<RelGymFeature> findRelOfGymWhereFeatureIsBadgeDesignation(Long gym_id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("gym_id"), Conditions.just(gym_id.toString()));
        Condition queryByStatus = Conditions.isEqual(entityTable.column("feature_id"), Conditions.just("'" + 222 + "'").and(whereClause));
        return createQuery(null, queryByStatus).one();
    }

    @Override
    public Flux<RelGymFeature> findFeaturesByGym(Pageable pageable, Long gym_id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("gym_id"), Conditions.just(gym_id.toString()));
        return createQuery(null, whereClause).all();
    }

    @Override
    public Mono<Long> countFeaturesByGym(Long gym_id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("gym_id"), Conditions.just(gym_id.toString()));
        return createQuery(null, whereClause).all().count();
    }

    @Override
    public Mono<RelGymFeature> findRelByFeatureAndGym(Long gym_id, Long feature_id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("gym_id"), Conditions.just(gym_id.toString()));
        Condition queryByStatus = Conditions.isEqual(entityTable.column("feature_id"), Conditions.just(feature_id.toString()).and(whereClause));
        return createQuery(null, queryByStatus).one();
    }
}
