package com.alphalab.repository;

import com.alphalab.domain.RelBadgePack;
import com.alphalab.domain.enumeration.BadgePackStatus;
import com.alphalab.repository.rowmapper.BadgeRowMapper;
import com.alphalab.repository.rowmapper.ExtBadgeDesignationRowMapper;
import com.alphalab.repository.rowmapper.OffrePackRowMapper;
import com.alphalab.repository.rowmapper.RelBadgePackRowMapper;
import com.alphalab.service.impl.RelBadgePackServiceImpl;
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
 * Spring Data R2DBC custom repository implementation for the RelBadgePack entity.
 */
@SuppressWarnings("unused")
class RelBadgePackRepositoryInternalImpl extends SimpleR2dbcRepository<RelBadgePack, Long> implements RelBadgePackRepositoryInternal {

    private final Logger log = LoggerFactory.getLogger(RelBadgePackServiceImpl.class);
    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final RelBadgePackRowMapper relBadgePackMapper;

    private final OffrePackRowMapper packRowMapper;
    private final BadgeRowMapper badgeRowMapper;
    private final ExtBadgeDesignationRowMapper extBadgeDesignationRowMapper;

    private static final Table entityTable = Table.aliased("rel_badge_pack", EntityManager.ENTITY_ALIAS);
    private static final Table packTable = Table.aliased("pack", "pack");
    private static final Table badgeTable = Table.aliased("badge", "badge");
    private static final Table extBadgeDesignationTable = Table.aliased("ext_badge_designation", "ext_badge_designation");

    public RelBadgePackRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        RelBadgePackRowMapper relBadgePackMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        OffrePackRowMapper packRowMapper,
        BadgeRowMapper badgeRowMapper,
        ExtBadgeDesignationRowMapper extBadgeDesignationRowMapper
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(RelBadgePack.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.relBadgePackMapper = relBadgePackMapper;
        this.packRowMapper = packRowMapper;
        this.badgeRowMapper = badgeRowMapper;
        this.extBadgeDesignationRowMapper = extBadgeDesignationRowMapper;
    }

    @Override
    public Flux<RelBadgePack> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<RelBadgePack> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = RelBadgePackSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(OffrePackSqlHelper.getColumns(packTable, "pack"));
        columns.addAll(BadgeSqlHelper.getColumns(badgeTable, "badge"));
        SelectBuilder.SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(packTable)
            .on(Column.create("pack_id", entityTable))
            .equals(Column.create("id", packTable))
            .leftOuterJoin(badgeTable)
            .on(Column.create("badge_id", entityTable))
            .equals(Column.create("id", badgeTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, RelBadgePack.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<RelBadgePack> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<RelBadgePack> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    @Override
    public Flux<RelBadgePack> findAllPacksByBadge(Long badge_id, Pageable pageable) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("badge_id"), Conditions.just(badge_id.toString()));
        return createQuery(pageable, whereClause).all();
    }

    @Override
    public Flux<RelBadgePack> findRelBadgePackByGym(Long gym_id, Pageable pageable) {
        Comparison whereClause = Conditions.isEqual(badgeTable.column("gym_id"), Conditions.just(gym_id.toString()));
        return createQuery(pageable, whereClause).all();
    }

    @Override
    public Mono<Long> countAllPacksByBadge(Long badge_id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("badge_id"), Conditions.just(badge_id.toString()));
        return createQuery(null, whereClause).all().count();
    }

    private RelBadgePack process(Row row, RowMetadata metadata) {
        RelBadgePack entity = relBadgePackMapper.apply(row, "e");
        entity.setPack(packRowMapper.apply(row, "pack"));
        entity.setBadge(badgeRowMapper.apply(row, "badge"));
        return entity;
    }

    private RelBadgePack processFull(Row row, RowMetadata metadata) {
        RelBadgePack entity = relBadgePackMapper.apply(row, "e");
        entity.setPack(packRowMapper.apply(row, "pack"));
        entity.setBadge(badgeRowMapper.apply(row, "badge"));
        entity.setExtBadgesDesignation(extBadgeDesignationRowMapper.apply(row, "ext_badge_designation"));
        return entity;
    }

    @Override
    public <S extends RelBadgePack> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Mono<RelBadgePack> findActiveByRelBadgePackId(String badge_uid) {
        Comparison queryByBadge = Conditions.isEqual(badgeTable.column("uid"), Conditions.just("'" + badge_uid.toString() + "'"));
        Condition queryByStatus = Conditions
            .isEqual(entityTable.column("status"), Conditions.just("'" + BadgePackStatus.ACTIVE.name() + "'"))
            .and(queryByBadge);
        return createQuery(null, queryByStatus).first();
    }

    @Override
    public Mono<RelBadgePack> findRelOfBadgeWhereStatusIsActive(Long badge_id) {
        Comparison queryByBadge = Conditions.isEqual(badgeTable.column("id"), Conditions.just("'" + badge_id.toString() + "'"));
        Condition queryByStatus = Conditions
            .isEqual(entityTable.column("status"), Conditions.just("'" + BadgePackStatus.ACTIVE.name() + "'"))
            .and(queryByBadge);
        return createQuery(null, queryByStatus).first();
    }

    RowsFetchSpec<RelBadgePack> createRawQuery(Long gym_id, Pageable pageable) {
        String select =
            "" +
            "select distinct on (badge.id) e.id AS e_id, e.enabled AS e_enabled, e.status AS e_status, e.pack_id AS e_pack_id, e.badge_id AS e_badge_id, e.created_date AS e_created_date, e.last_modified_date AS e_last_modified_date, e.created_by AS e_created_by, e.last_modified_by AS e_last_modified_by, \n" +
            "\n" +
            "pack.id AS pack_id, pack.name AS pack_name, pack.description AS pack_description, pack.workout_sessions AS pack_workout_sessions, pack.price AS pack_price, pack.auto_confirm AS pack_auto_confirm, pack.rdv_enabled AS pack_rdv_enabled, pack.gym_id AS pack_gym_id, pack.created_date AS pack_created_date, pack.last_modified_date AS pack_last_modified_date, pack.created_by AS pack_created_by, pack.last_modified_by AS pack_last_modified_by, \n" +
            "badge.id AS badge_id, badge.uid AS badge_uid, badge.status AS badge_status, badge.enabled AS badge_enabled, badge.gym_id AS badge_gym_id, badge.created_date AS badge_created_date, badge.last_modified_date AS badge_last_modified_date, badge.created_by AS badge_created_by, badge.last_modified_by AS badge_last_modified_by, \n" +
            "\n" +
            "ext_badge_designation.id AS ext_badge_designation_id, ext_badge_designation.status AS ext_badge_designation_status, ext_badge_designation.badge_id AS ext_badge_designation_badge_id, ext_badge_designation.user_id AS ext_badge_designation_user_id, ext_badge_designation.created_date AS ext_badge_designation_created_date, ext_badge_designation.last_modified_date AS ext_badge_designation_last_modified_date, ext_badge_designation.created_by AS ext_badge_designation_created_by, ext_badge_designation.last_modified_by AS ext_badge_designation_last_modified_by \n" +
            "\n" +
            "FROM badge badge \n" +
            "LEFT OUTER JOIN rel_badge_pack e ON badge.id = e.badge_id \n" +
            "LEFT OUTER JOIN pack pack ON e.pack_id = pack.id \n" +
            "LEFT OUTER JOIN ext_badge_designation ext_badge_designation ON badge.id = ext_badge_designation.badge_id \n" +
            "WHERE badge.gym_id = " +
            gym_id +
            " " +
            "order by badge.id, e.created_date  desc\n" +
            "LIMIT " +
            pageable.getPageSize() + " " +
            "OFFSET " +
            pageable.getPageNumber();
        return db.sql(select).map(this::processFull);
    }

    RowsFetchSpec<RelBadgePack> createCountRawQuery(Long gym_id) {
        String select =
            "" +
            "select distinct on (badge.id) e.id AS e_id, e.enabled AS e_enabled, e.status AS e_status, e.pack_id AS e_pack_id, e.badge_id AS e_badge_id, e.created_date AS e_created_date, e.last_modified_date AS e_last_modified_date, e.created_by AS e_created_by, e.last_modified_by AS e_last_modified_by, \n" +
            "\n" +
            "pack.id AS pack_id, pack.name AS pack_name, pack.description AS pack_description, pack.workout_sessions AS pack_workout_sessions, pack.price AS pack_price, pack.auto_confirm AS pack_auto_confirm, pack.rdv_enabled AS pack_rdv_enabled, pack.gym_id AS pack_gym_id, pack.created_date AS pack_created_date, pack.last_modified_date AS pack_last_modified_date, pack.created_by AS pack_created_by, pack.last_modified_by AS pack_last_modified_by, \n" +
            "badge.id AS badge_id, badge.uid AS badge_uid, badge.status AS badge_status, badge.enabled AS badge_enabled, badge.gym_id AS badge_gym_id, badge.created_date AS badge_created_date, badge.last_modified_date AS badge_last_modified_date, badge.created_by AS badge_created_by, badge.last_modified_by AS badge_last_modified_by, \n" +
            "\n" +
            "ext_badge_designation.id AS ext_badge_designation_id, ext_badge_designation.status AS ext_badge_designation_status, ext_badge_designation.badge_id AS ext_badge_designation_badge_id, ext_badge_designation.user_id AS ext_badge_designation_user_id, ext_badge_designation.created_date AS ext_badge_designation_created_date, ext_badge_designation.last_modified_date AS ext_badge_designation_last_modified_date, ext_badge_designation.created_by AS ext_badge_designation_created_by, ext_badge_designation.last_modified_by AS ext_badge_designation_last_modified_by \n" +
            "\n" +
            "FROM badge badge \n" +
            "LEFT OUTER JOIN rel_badge_pack e ON badge.id = e.badge_id \n" +
            "LEFT OUTER JOIN pack pack ON e.pack_id = pack.id \n" +
            "LEFT OUTER JOIN ext_badge_designation ext_badge_designation ON badge.id = ext_badge_designation.badge_id \n" +
            "WHERE badge.gym_id = " +
            gym_id +
            " " +
            "order by badge.id, e.created_date  desc";
        return db.sql(select).map(this::processFull);
    }

    @Override
    public Flux<RelBadgePack> findAllBadgesWithOrWithoutPack(Long gym_id, Pageable pageable) {
        return createRawQuery(gym_id, pageable).all();
    }

    @Override
    public Mono<Long> countAllBadgesWithOrWithoutPack(Long gym_id) {
        return createCountRawQuery(gym_id).all().count();
    }

    @Override
    public Mono<RelBadgePack> findRelBadgePackByBadge(Long badge_id) {
        Comparison queryWhereActive = Conditions.isEqual(entityTable.column("status"), Conditions.just("'" + BadgePackStatus.ACTIVE + "'"));
        Comparison whereClause = Conditions.isEqual(
            entityTable.column("badge_id"),
            Conditions.just(badge_id.toString()).and(queryWhereActive)
        );
        return createQuery(null, whereClause).one();
    }
}
