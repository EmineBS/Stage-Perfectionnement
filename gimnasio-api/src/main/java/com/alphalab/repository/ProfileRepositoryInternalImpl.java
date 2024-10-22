package com.alphalab.repository;

import com.alphalab.domain.Profile;
import com.alphalab.repository.rowmapper.ProfileRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the Profile entity.
 */
@SuppressWarnings("unused")
class ProfileRepositoryInternalImpl extends SimpleR2dbcRepository<Profile, Long> implements ProfileRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final ProfileRowMapper profileMapper;

    private static final Table entityTable = Table.aliased("profile", EntityManager.ENTITY_ALIAS);

    public ProfileRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        ProfileRowMapper profileMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Profile.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.profileMapper = profileMapper;
    }

    @Override
    public Flux<Profile> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Profile> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = ProfileSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Profile.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Profile> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Profile> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Profile process(Row row, RowMetadata metadata) {
        Profile entity = profileMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends Profile> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Mono<Profile> findCurrentProfile(String userId) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("email"), Conditions.just("'" + userId.toString() + "'"));
        return createQuery(null, whereClause).one();
    }

    @Override
    public Mono<Profile> findByBadge(Long badge_id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("badge_id"), Conditions.just("'" + badge_id.toString() + "'"));
        return createQuery(null, whereClause).one();
    }
}
