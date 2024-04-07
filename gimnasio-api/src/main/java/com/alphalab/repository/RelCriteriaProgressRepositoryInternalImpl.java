package com.alphalab.repository;

import com.alphalab.domain.RelCriteriaProgress;
import com.alphalab.repository.rowmapper.RelCriteriaProgressRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the RelCriteriaProgress entity.
 */
@SuppressWarnings("unused")
class RelCriteriaProgressRepositoryInternalImpl
    extends SimpleR2dbcRepository<RelCriteriaProgress, Long>
    implements RelCriteriaProgressRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final RelCriteriaProgressRowMapper relCriteriaProgressMapper;

    private static final Table entityTable = Table.aliased("relCriteriaProgress", EntityManager.ENTITY_ALIAS);

    public RelCriteriaProgressRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        RelCriteriaProgressRowMapper relCriteriaProgressMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(RelCriteriaProgress.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.relCriteriaProgressMapper = relCriteriaProgressMapper;
    }

    @Override
    public Flux<RelCriteriaProgress> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<RelCriteriaProgress> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = RelCriteriaProgressSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, RelCriteriaProgress.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<RelCriteriaProgress> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<RelCriteriaProgress> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private RelCriteriaProgress process(Row row, RowMetadata metadata) {
        RelCriteriaProgress entity = relCriteriaProgressMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends RelCriteriaProgress> Mono<S> save(S entity) {
        return super.save(entity);
    }
}
