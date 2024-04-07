package com.alphalab.repository;

import com.alphalab.domain.RDV;
import com.alphalab.domain.enumeration.BadgePackStatus;
import com.alphalab.domain.enumeration.RDVStatus;
import com.alphalab.repository.rowmapper.*;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Spring Data R2DBC custom repository implementation for the RDV entity.
 */
@SuppressWarnings("unused")
class RDVRepositoryInternalImpl extends SimpleR2dbcRepository<RDV, Long> implements RDVRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final RDVRowMapper rdvMapper;

    private final OffrePackRowMapper packRowMapper;
    private final BadgeRowMapper badgeRowMapper;
    private final ProfileRowMapper profileRowMapper;

    private final RelBadgePackRowMapper relBadgePackRowMapper;

    private final Logger log = LoggerFactory.getLogger(RDVRepositoryInternalImpl.class);
    private static final Table entityTable = Table.aliased("rdv", EntityManager.ENTITY_ALIAS);

    private static final Table badgeTable = Table.aliased("badge", "badge");
    private static final Table packTable = Table.aliased("pack", "pack");
    private static final Table profileTable = Table.aliased("profile", "profile");
    private static final Table relBadgePackTable = Table.aliased("rel_badge_pack", "rel_badge_pack");

    public RDVRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        RDVRowMapper rdvMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        OffrePackRowMapper packRowMapper,
        BadgeRowMapper badgeRowMapper,
        ProfileRowMapper profileRowMapper,
        RelBadgePackRowMapper relBadgePackRowMapper
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(RDV.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.rdvMapper = rdvMapper;
        this.packRowMapper = packRowMapper;
        this.badgeRowMapper = badgeRowMapper;
        this.profileRowMapper = profileRowMapper;
        this.relBadgePackRowMapper = relBadgePackRowMapper;
    }

    @Override
    public Flux<RDV> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<RDV> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = RDVSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(RelBadgePackSqlHelper.getColumns(relBadgePackTable, "rel_badge_pack"));
        columns.addAll(OffrePackSqlHelper.getColumns(packTable, "pack"));
        columns.addAll(BadgeSqlHelper.getColumns(badgeTable, "badge"));
        columns.addAll(ProfileSqlHelper.getColumns(profileTable, "profile"));
        SelectBuilder.SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(relBadgePackTable)
            .on(Column.create("rel_badge_pack_id", entityTable))
            .equals(Column.create("id", relBadgePackTable))
            .leftOuterJoin(packTable)
            .on(Column.create("pack_id", relBadgePackTable))
            .equals(Column.create("id", packTable))
            .leftOuterJoin(badgeTable)
            .on(Column.create("badge_id", relBadgePackTable))
            .equals(Column.create("id", badgeTable))
            .leftOuterJoin(profileTable)
            .on(Column.create("id", badgeTable))
            .equals(Column.create("badge_id", profileTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, RDV.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<RDV> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<RDV> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private RDV process(Row row, RowMetadata metadata) {
        RDV entity = rdvMapper.apply(row, "e");
        entity.setRelBadgePack(relBadgePackRowMapper.apply(row, "rel_badge_pack"));
        entity.getRelBadgePack().setPack(packRowMapper.apply(row, "pack"));
        entity.getRelBadgePack().setBadge(badgeRowMapper.apply(row, "badge"));
        entity.setProfile(profileRowMapper.apply(row, "profile"));
        return entity;
    }

    @Override
    public <S extends RDV> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<RDV> findAllByBadge(Long badge_id, Pageable pageable) {
        Comparison whereClause = Conditions.isEqual(relBadgePackTable.column("badge_id"), Conditions.just(badge_id.toString()));
        return createQuery(pageable, whereClause).all();
    }

    @Override
    public Mono<Long> countAllByBadge(Long badge_id) {
        Comparison whereClause = Conditions.isEqual(relBadgePackTable.column("badge_id"), Conditions.just(badge_id.toString()));
        return createQuery(null, whereClause).all().count();
    }

    @Override
    public Mono<Long> countAllByBadgeAndStatusIsConfirmed(Long badge_id) {
        Comparison queryByBadge = Conditions.isEqual(relBadgePackTable.column("badge_id"), Conditions.just(badge_id.toString()));
        Condition queryByStatus = Conditions
            .isEqual(entityTable.column("status"), Conditions.just("'" + RDVStatus.CONFIRMED.name() + "'"))
            .and(queryByBadge);
        return createQuery(null, queryByStatus).all().count();
    }

    @Override
    public Flux<RDV> findAllByGym(Long gym_id, Pageable pageable) {
        Comparison whereClause = Conditions.isEqual(packTable.column("gym_id"), Conditions.just(gym_id.toString()));
        return createQuery(pageable, whereClause).all();
    }

    @Override
    public Mono<Long> countAllByGym(Long gym_id) {
        Comparison whereClause = Conditions.isEqual(packTable.column("gym_id"), Conditions.just(gym_id.toString()));
        return createQuery(null, whereClause).all().count();
    }

    @Override
    public Flux<RDV> findAllOfCurrentBadgeAccount(String email) {
        Comparison whereClause = Conditions.isEqual(profileTable.column("email"), Conditions.just("'" + email.toString() + "'"));
        return createQuery(null, whereClause).all();
    }

    @Override
    public Flux<RDV> findAllExceptOfCurrentBadgeAccount(String email) {
        Comparison whereClause = Conditions.isNotEqual(profileTable.column("email"), Conditions.just("'" + email.toString() + "'"));
        return createQuery(null, whereClause).all();
    }
}
