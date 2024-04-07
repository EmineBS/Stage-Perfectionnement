package com.alphalab.repository;

import com.alphalab.domain.Feature;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Feature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FeatureRepository extends ReactiveCrudRepository<Feature, Long>, FeatureRepositoryInternal {
    Flux<Feature> findAllBy(Pageable pageable);

    @Override
    <S extends Feature> Mono<S> save(S entity);

    @Override
    Flux<Feature> findAll();

    @Override
    Mono<Feature> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);

    @Override
    Flux<Feature> findAllByGymId(Long gym_id);

    Flux<Feature> findAllByGym(Pageable pageable, Long gym_id);
    @Override
    Mono<Long> countAllByGym(Long gym_id);
}

interface FeatureRepositoryInternal {
    <S extends Feature> Mono<S> save(S entity);

    Flux<Feature> findAllBy(Pageable pageable);

    Flux<Feature> findAll();

    Mono<Feature> findById(Long id);

    Flux<Feature> findAllByGymId(Long gym_id);

    Flux<Feature> findAllByGym(Pageable pageable, Long gym_id);

    Mono<Long> countAllByGym(java.lang.Long gym_id);
}
