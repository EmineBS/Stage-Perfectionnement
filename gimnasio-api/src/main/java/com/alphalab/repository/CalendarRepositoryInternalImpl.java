package com.alphalab.repository;

import com.alphalab.domain.Calendar;
import com.alphalab.repository.rowmapper.BadgeRowMapper;
import com.alphalab.repository.rowmapper.CalendarRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the Calendar entity.
 */
@SuppressWarnings("unused")
class CalendarRepositoryInternalImpl extends SimpleR2dbcRepository<Calendar, Long> implements CalendarRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final CalendarRowMapper calendarMapper;
    private final GymRowMapper gymRowMapper;

    private static final Table entityTable = Table.aliased("calendar", EntityManager.ENTITY_ALIAS);
    private static final Table gymTable = Table.aliased("gym", "gym");

    public CalendarRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        CalendarRowMapper calendarMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter,
        GymRowMapper gymRowMapper
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Calendar.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.calendarMapper = calendarMapper;
        this.gymRowMapper = gymRowMapper;
    }

    @Override
    public Flux<Calendar> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Calendar> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = CalendarSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        columns.addAll(GymSqlHelper.getColumns(gymTable, "gym"));
        SelectBuilder.SelectFromAndJoinCondition selectFrom = Select
            .builder()
            .select(columns)
            .from(entityTable)
            .leftOuterJoin(gymTable)
            .on(Column.create("gym_id", entityTable))
            .equals(Column.create("id", gymTable));
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Calendar.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Calendar> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Calendar> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Calendar process(Row row, RowMetadata metadata) {
        Calendar entity = calendarMapper.apply(row, "e");
        entity.setGym(gymRowMapper.apply(row, "gym"));
        return entity;
    }

    @Override
    public <S extends Calendar> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Mono<Calendar> findCalendarByGym(Long gym_id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("gym_id"), Conditions.just(gym_id.toString()));
        return createQuery(null, whereClause).one();
    }
}
