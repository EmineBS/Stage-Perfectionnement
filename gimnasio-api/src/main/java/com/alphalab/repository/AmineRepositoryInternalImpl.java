package com.alphalab.repository;

import com.alphalab.domain.Amine;
import com.alphalab.repository.rowmapper.AmineRowMapper;
import com.alphalab.repository.rowmapper.GymRowMapper;
import io.r2dbc.spi.Row;
import io.r2dbc.spi.RowMetadata;
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

import java.util.List;

/**
 * Spring Data R2DBC custom repository implementation for the Badge entity.
 */
@SuppressWarnings("unused")
class AmineRepositoryInternalImpl extends SimpleR2dbcRepository<Amine, Long> implements AmineRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final AmineRowMapper amineRowMapper;

    private static final Table entityTable = Table.aliased("amine", EntityManager.ENTITY_ALIAS);

    public AmineRepositoryInternalImpl(
            R2dbcEntityTemplate template,
            EntityManager entityManager,
            R2dbcEntityOperations entityOperations,
            R2dbcConverter converter,
            GymRowMapper gymRowMapper,
            AmineRowMapper amineRowMapper
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Amine.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.amineRowMapper = amineRowMapper;
    }

    @Override
    public Flux<Amine> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Amine> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = AmineSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectBuilder.SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Amine.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Amine> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Amine> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Amine process(Row row, RowMetadata metadata) {
        Amine entity = amineRowMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends Amine> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
