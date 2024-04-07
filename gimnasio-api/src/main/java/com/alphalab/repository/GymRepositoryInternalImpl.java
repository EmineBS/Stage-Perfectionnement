package com.alphalab.repository;

import com.alphalab.domain.Gym;
import com.alphalab.repository.rowmapper.GymRowMapper;
import com.alphalab.repository.rowmapper.UserRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the Gym entity.
 */
@SuppressWarnings("unused")
class GymRepositoryInternalImpl extends SimpleR2dbcRepository<Gym, Long> implements GymRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final GymRowMapper gymMapper;

    private static final Table entityTable = Table.aliased("gym", EntityManager.ENTITY_ALIAS);

    public GymRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        GymRowMapper gymMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Gym.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.gymMapper = gymMapper;
    }

    @Override
    public Flux<Gym> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Gym> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = GymSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Gym.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Gym> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Gym> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Gym process(Row row, RowMetadata metadata) {
        Gym entity = gymMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends Gym> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
