package com.alphalab.repository;

import com.alphalab.domain.Criteria;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Criteria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CriteriaRepository extends ReactiveCrudRepository<Criteria, Long>, CriteriaRepositoryInternal {
    Flux<Criteria> findAllBy(Pageable pageable);

    @Override
    <S extends Criteria> Mono<S> save(S entity);

    @Override
    Flux<Criteria> findAll();

    @Override
    Mono<Criteria> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);

    @Override
    Flux<Criteria> findByGymId(Pageable pageable, Long gym_id);

    @Override
    Mono<Long> countByGymId(Long gym_id);

    @Override
    Flux<Criteria> findAllOfGym(Long gym_id);
}

interface CriteriaRepositoryInternal {
    <S extends Criteria> Mono<S> save(S entity);

    Flux<Criteria> findAllBy(Pageable pageable);

    Flux<Criteria> findAll();

    Mono<Criteria> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Criteria> findAllBy(Pageable pageable, Criteria criteria);
    //    Flux<Criteria> findAllCriteriasByUser(String id_user, Pageable pageable);
    //    Mono<Long> countAllCriteriasByUser(String id_user);
    Flux<Criteria> findByGymId(Pageable pageable, Long gym_id);
    Mono<Long> countByGymId(Long gym_id);

    Flux<Criteria> findAllOfGym(Long gym_id);
}
