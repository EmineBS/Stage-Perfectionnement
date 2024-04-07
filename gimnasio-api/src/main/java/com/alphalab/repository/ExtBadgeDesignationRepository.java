package com.alphalab.repository;

import com.alphalab.domain.ExtBadgeDesignation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the ExtBadgeDesignation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ExtBadgeDesignationRepository
    extends ReactiveCrudRepository<ExtBadgeDesignation, Long>, ExtBadgeDesignationRepositoryInternal {
    Flux<ExtBadgeDesignation> findAllBy(Pageable pageable);

    @Override
    <S extends ExtBadgeDesignation> Mono<S> save(S entity);

    @Override
    Flux<ExtBadgeDesignation> findAll();

    @Override
    Mono<ExtBadgeDesignation> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);

    @Override
    Mono<ExtBadgeDesignation> findByBadge(Long badge_id);
}

interface ExtBadgeDesignationRepositoryInternal {
    <S extends ExtBadgeDesignation> Mono<S> save(S entity);

    Flux<ExtBadgeDesignation> findAllBy(Pageable pageable);

    Flux<ExtBadgeDesignation> findAll();

    Mono<ExtBadgeDesignation> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<ExtBadgeDesignation> findAllBy(Pageable pageable, Criteria criteria);

    Mono<ExtBadgeDesignation> findByBadge(Long badge_id);
}
