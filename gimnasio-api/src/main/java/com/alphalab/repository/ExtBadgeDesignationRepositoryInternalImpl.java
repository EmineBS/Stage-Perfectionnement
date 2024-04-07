package com.alphalab.repository;

import com.alphalab.domain.ExtBadgeDesignation;
import com.alphalab.repository.rowmapper.ExtBadgeDesignationRowMapper;
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
 * Spring Data R2DBC custom repository implementation for the ExtBadgeDesignation entity.
 */
@SuppressWarnings("unused")
class ExtBadgeDesignationRepositoryInternalImpl
    extends SimpleR2dbcRepository<ExtBadgeDesignation, Long>
    implements ExtBadgeDesignationRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final ExtBadgeDesignationRowMapper extBadgeDesignationMapper;

    private static final Table entityTable = Table.aliased("ext_badge_designation", EntityManager.ENTITY_ALIAS);

    public ExtBadgeDesignationRepositoryInternalImpl(
        R2dbcEntityTemplate template,
        EntityManager entityManager,
        ExtBadgeDesignationRowMapper extBadgeDesignationMapper,
        R2dbcEntityOperations entityOperations,
        R2dbcConverter converter
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(ExtBadgeDesignation.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.extBadgeDesignationMapper = extBadgeDesignationMapper;
    }

    @Override
    public Flux<ExtBadgeDesignation> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<ExtBadgeDesignation> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = ExtBadgeDesignationSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, ExtBadgeDesignation.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<ExtBadgeDesignation> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<ExtBadgeDesignation> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private ExtBadgeDesignation process(Row row, RowMetadata metadata) {
        ExtBadgeDesignation entity = extBadgeDesignationMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends ExtBadgeDesignation> Mono<S> save(S entity) {
        return super.save(entity);
    }

    @Override
    public Mono<ExtBadgeDesignation> findByBadge(Long badge_id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("badge_id"), Conditions.just(badge_id.toString()));
        return createQuery(null, whereClause).one();
    }
}
