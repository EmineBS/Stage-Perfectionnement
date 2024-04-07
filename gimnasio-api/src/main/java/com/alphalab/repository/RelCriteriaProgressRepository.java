package com.alphalab.repository;

import com.alphalab.domain.RDV;
import com.alphalab.domain.RelCriteriaProgress;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the RelCriteriaProgress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelCriteriaProgressRepository
    extends ReactiveCrudRepository<RelCriteriaProgress, Long>, RelCriteriaProgressRepositoryInternal {
    Flux<RelCriteriaProgress> findAllBy(Pageable pageable);

    @Override
    <S extends RelCriteriaProgress> Mono<S> save(S entity);

    @Override
    Flux<RelCriteriaProgress> findAll();

    @Override
    Mono<RelCriteriaProgress> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);

    @Query(
        "SELECT * " +
        "FROM rel_criteria_progress entity " +
        "join progress p " +
        "on p.id = entity.progress_id " +
        "join criteria c " +
        "on c.id = entity.criteria_id " +
        "where p.badge_id = :badge_id " +
        "and c.name = :criteria " +
        "order by entity.created_date"
    )
    Flux<RelCriteriaProgress> findProgresssByBadgeAndCriteria(Long badge_id, String criteria);
}

interface RelCriteriaProgressRepositoryInternal {
    <S extends RelCriteriaProgress> Mono<S> save(S entity);

    Flux<RelCriteriaProgress> findAllBy(Pageable pageable);

    Flux<RelCriteriaProgress> findAll();

    Mono<RelCriteriaProgress> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
}
