package com.alphalab.repository;

import com.alphalab.domain.Game;
import com.alphalab.repository.rowmapper.GameRowMapper;
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

@SuppressWarnings("unused")
class GameRepositoryInternalImpl extends SimpleR2dbcRepository<Game, Long> implements GameRepositoryInternal {

    private final DatabaseClient db;
    private final R2dbcEntityTemplate r2dbcEntityTemplate;
    private final EntityManager entityManager;

    private final GameRowMapper gameRowMapper;

    private static final Table entityTable = Table.aliased("game", EntityManager.ENTITY_ALIAS);

    public GameRepositoryInternalImpl(
            R2dbcEntityTemplate template,
            EntityManager entityManager,
            R2dbcEntityOperations entityOperations,
            R2dbcConverter converter,
            GameRowMapper gameRowMapper
    ) {
        super(
            new MappingRelationalEntityInformation(converter.getMappingContext().getRequiredPersistentEntity(Game.class)),
            entityOperations,
            converter
        );
        this.db = template.getDatabaseClient();
        this.r2dbcEntityTemplate = template;
        this.entityManager = entityManager;
        this.gameRowMapper = gameRowMapper;
    }

    @Override
    public Flux<Game> findAllBy(Pageable pageable) {
        return createQuery(pageable, null).all();
    }

    RowsFetchSpec<Game> createQuery(Pageable pageable, Condition whereClause) {
        List<Expression> columns = GameSqlHelper.getColumns(entityTable, EntityManager.ENTITY_ALIAS);
        SelectBuilder.SelectFromAndJoin selectFrom = Select.builder().select(columns).from(entityTable);
        // we do not support Criteria here for now as of https://github.com/jhipster/generator-jhipster/issues/18269
        String select = entityManager.createSelect(selectFrom, Game.class, pageable, whereClause);
        return db.sql(select).map(this::process);
    }

    @Override
    public Flux<Game> findAll() {
        return findAllBy(null);
    }

    @Override
    public Mono<Game> findById(Long id) {
        Comparison whereClause = Conditions.isEqual(entityTable.column("id"), Conditions.just(id.toString()));
        return createQuery(null, whereClause).one();
    }

    private Game process(Row row, RowMetadata metadata) {
        Game entity = gameRowMapper.apply(row, "e");
        return entity;
    }

    @Override
    public <S extends Game> Mono<S> save(S entity) {
        return super.save(entity);
    }


}
