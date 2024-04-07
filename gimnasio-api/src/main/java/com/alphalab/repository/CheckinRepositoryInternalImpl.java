package com.alphalab.repository;

import com.alphalab.domain.CheckIn;
import com.alphalab.repository.rowmapper.BadgeRowMapper;
import com.alphalab.repository.rowmapper.CheckinRowMapper;
import com.alphalab.repository.rowmapper.OffrePackRowMapper;
import com.alphalab.repository.rowmapper.RelBadgePackRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the Checkin entity.
 */
@SuppressWarnings("unused")
class CheckinRepositoryInternalImpl extends SimpleR2dbcRepository<CheckIn, Long> implements CheckinRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final CheckinRowMapper checkinRepository;
    private final RelBadgePackRowMapper relBadgePackMapper;
    private final BadgeRowMapper badgeMapper;
    private final OffrePackRowMapper packMapper;

    private static final Table entityTable = Table.aliased("checkin", EntityManager.ENTITY_ALIAS);
    private static final Table relBadgePackTable = Table.aliased("rel_badge_pack", "rel_badge_pack");
    private static final Table packTable = Table.aliased("pack", "pack");
    private static final Table badgeTable = Table.aliased("badge", "badge");

    public CheckinRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        CheckinRowMapper checkinRepository,
        RelBadgePackRowMapper relBadgePackMapper,
        BadgeRowMapper badgeMapper,
        OffrePackRowMapper packMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(CheckIn.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.checkinRepository = checkinRepository;
        this.relBadgePackMapper = relBadgePackMapper;
        this.badgeMapper = badgeMapper;
        this.packMapper = packMapper;
    }

    @Override
    public Flux<CheckIn> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<CheckIn> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = CheckinSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(RelBadgePackSqlHelper.getColumns(relBadgePackTable, "rbp"));
        columns.addAll(BadgeSqlHelper.getColumns(badgeTable, "b"));
        columns.addAll(OffrePackSqlHelper.getColumns(packTable, "p"));
        SelectBuilder.SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(relBadgePackTable)
            .on(Column.create("id", relBadgePackTable))
            .equals(Column.create("rel_badge_pack_id", entityTable))
            .join(badgeTable)
            .on(Column.create("id", badgeTable))
            .equals(Column.create("badge_id", relBadgePackTable))
            .join(packTable)
            .on(Column.create("id", packTable))
            .equals(Column.create("pack_id", relBadgePackTable));
        String select = entityManager.createSelect(selectFrom, CheckIn.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<CheckIn> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<CheckIn> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private CheckIn process(Row row, RowMetadata metadata) {
        CheckIn entity = checkinRepository.apply(row, "e");
        entity.setRelBadgePack(relBadgePackMapper.apply(row, "rbp"));
        entity.getRelBadgePack().setBadge(badgeMapper.apply(row, "b"));
        entity.getRelBadgePack().setPack(packMapper.apply(row, "p"));
        return entity;
    }

    @Override
    public <S extends CheckIn> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Flux<CheckIn> findAllByUserId(Pageable pageable, String userId) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("user_id"), Conditions.just("'" + userId.toString() + "'"));
        return createQuery(pageable, whereClause).all();
    }

    @Override
    public Flux<CheckIn> findAllByBadgeUID(Pageable pageable, String badge_uid) {
        Comparison whereClause = Conditions.isEqual(badgeTable.column("uid"), Conditions.just("'" + badge_uid.toString() + "'"));
        return createQuery(pageable, whereClause).all();
    }

    @Override
    public Mono<Long> countAllByBadgeUID(String badge_uid) {
        Comparison whereClause = Conditions.isEqual(badgeTable.column("uid"), Conditions.just("'" + badge_uid.toString() + "'"));
        return createQuery(null, whereClause).all().count();
    }

    @Override
    public Flux<CheckIn> findAllByGym(Pageable pageable, Long gym_id) {
        Comparison whereClause = Conditions.isEqual(badgeTable.column("gym_id"), Conditions.just("'" + gym_id.toString() + "'"));
        return createQuery(pageable, whereClause).all();
    }

    @Override
    public Mono<Long> countAllByGym(Long gym_id) {
        Comparison whereClause = Conditions.isEqual(badgeTable.column("gym_id"), Conditions.just("'" + gym_id.toString() + "'"));
        return createQuery(null, whereClause).all().count();
    }
}
