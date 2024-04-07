package com.alphalab.repository;

import com.alphalab.domain.Feature;
import com.alphalab.repository.rowmapper.FeatureRowMapper;
import com.alphalab.service.dto.FeatureDTO;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.convert.R2dbcConverter;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.r2dbc.repository.support.SimpleR2dbcRepository;
import org.springframework.data.relational.core.sql.*;
import org.springframework.data.relational.core.sql.SelectBuilder.SelectFromAndJoin;
import org.springframework.data.relational.repository.support.MappingRelationalEntityInformation;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.r2dbc.core.RowsFetchSpec;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC custom repository implementation for the Feature entity.
 */
@SuppressWarnings("unused")
class FeatureRepositoryInternalImpl extends SimpleR2dbcRepository<Feature, Long> implements FeatureRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final FeatureRowMapper featureMapper;

    private static final Table entityTable = Table.aliased("feature", EntityManager.ENTITY_ALIAS);
    private static final Table relGymFeatureTable = Table.aliased("rel_gym_feature", "rel_gym_feature");

    public FeatureRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        FeatureRowMapper featureMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Feature.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.featureMapper = featureMapper;
    }

    @Override
    public Flux<Feature> findAllBy(Pageable pageable) {
        return createAllQuery(pageable, null).all();
    }

    RowsFetchSpec<Feature> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = FeatureSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectBuilder.SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(relGymFeatureTable)
            .on(Column.create("id", entityTable))
            .equals(Column.create("feature_id", relGymFeatureTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Feature.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    RowsFetchSpec<Feature> createAllQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = FeatureSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Feature.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Feature> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Feature> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createAllQuery(null, whereClause).one();
    }

    private Feature process(Row row, RowMetadata metadata) {
        Feature entity = featureMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends Feature> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<Feature> findAllByGymId(Long gym_id) {
        Comparison whereClause = Conditions.isEqual(relGymFeatureTable.column("gym_id"), Conditions.just(gym_id.toString()));
        return createQuery(null, whereClause).all();
    }

    @Override
    public Flux<Feature> findAllByGym(Pageable pageable, Long gym_id) {
        Comparison whereClause = Conditions.isEqual(relGymFeatureTable.column("gym_id"), Conditions.just(gym_id.toString()));
        return createQuery(null, whereClause).all();
    }

    @Override
    public Mono<Long> countAllByGym(Long gym_id) {
        Comparison whereClause = Conditions.isEqual(relGymFeatureTable.column("gym_id"), Conditions.just(gym_id.toString()));
        return createQuery(null, whereClause).all().count();
    }
}
