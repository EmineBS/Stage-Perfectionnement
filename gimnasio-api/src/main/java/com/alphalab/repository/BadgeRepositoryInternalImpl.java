package com.alphalab.repository;

import com.alphalab.domain.Badge;
import com.alphalab.repository.rowmapper.BadgeRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the Badge entity.
 */
@SuppressWarnings("unused")
class BadgeRepositoryInternalImpl extends SimpleR2dbcRepository<Badge, Long> implements BadgeRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final BadgeRowMapper badgeMapper;

    private final GymRowMapper gymRowMapper;

    private static final Table entityTable = Table.aliased("badge", EntityManager.ENTITY_ALIAS);
    private static final Table gymTable = Table.aliased("gym", "gym");
    private static final Table profileTable = Table.aliased("profile", "profile");

    public BadgeRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        BadgeRowMapper badgeMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        GymRowMapper gymRowMapper
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Badge.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.badgeMapper = badgeMapper;
        this.gymRowMapper = gymRowMapper;
    }

    @Override
    public Flux<Badge> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Badge> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = BadgeSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(GymSqlHelper.getColumns(gymTable, "gym"));
        SelectBuilder.SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(gymTable)
            .on(Column.create("gym_id", entityTable))
            .equals(Column.create("id", gymTable))
            .leftOuterJoin(profileTable)
            .on(Column.create("id", entityTable))
            .equals(Column.create("badge_id", profileTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Badge.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Badge> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Badge> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Badge process(Row row, RowMetadata metadata) {
        Badge entity = badgeMapper.apply(row, "e");
        entity.setGym(gymRowMapper.apply(row, "gym"));
        return entity;
    }

    @Override
    public <S extends Badge> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<Badge> findAllBadgesOfGym(Long gym_id, Pageable pageable) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("gym_id"), Conditions.just(gym_id.toString()));
        return createQuery(pageable, whereClause).all();
    }

    @Override
    public Mono<Long> countFindAllBadgesOfGym(Long gym_id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("gym_id"), Conditions.just(gym_id.toString()));
        return createQuery(null, whereClause).all().count();
    }

    @Override
    public Mono<Badge> findOneBySession(String email) {
        Comparison whereClause = Conditions.isEqual(profileTable.column("email"), Conditions.just("'" + email.toString() + "'"));
        return createQuery(null, whereClause).one();
    }
}
