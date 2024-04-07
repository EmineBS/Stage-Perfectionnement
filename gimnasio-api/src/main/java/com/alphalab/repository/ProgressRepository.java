package com.alphalab.repository;

import com.alphalab.domain.Progress;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Progress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgressRepository extends ReactiveCrudRepository<Progress, Long>, ProgressRepositoryInternal {
    Flux<Progress> findAllBy(Pageable pageable);

    @Override
    <S extends Progress> Mono<S> save(S entity);

    @Override
    Flux<Progress> findAll();

    @Override
    Mono<Progress> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface ProgressRepositoryInternal {
    <S extends Progress> Mono<S> save(S entity);

    Flux<Progress> findAllBy(Pageable pageable);

    Flux<Progress> findAll();

    Mono<Progress> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Progress> findAllBy(Pageable pageable, Criteria criteria);
    //    Flux<Progress> findAllProgresssByUser(String id_user, Pageable pageable);
    //    Mono<Long> countAllProgresssByUser(String id_user);
}
