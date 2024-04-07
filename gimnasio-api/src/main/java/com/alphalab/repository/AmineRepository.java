package com.alphalab.repository;

import com.alphalab.domain.Amine;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Badge entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AmineRepository extends ReactiveCrudRepository<Amine, Long>, AmineRepositoryInternal {
    Flux<Amine> findAllBy(Pageable pageable);

    @Override
    <S extends Amine> Mono<S> save(S entity);

    @Override
    Flux<Amine> findAll();

    @Override
    Mono<Amine> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);
}

interface AmineRepositoryInternal {
    <S extends Amine> Mono<S> save(S entity);

    Flux<Amine> findAllBy(Pageable pageable);

    Flux<Amine> findAll();

    Mono<Amine> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Badge> findAllBy(Pageable pageable, Criteria criteria);

}
