package com.alphalab.repository;

import com.alphalab.domain.Badge;
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
public interface BadgeRepository extends ReactiveCrudRepository<Badge, Long>, BadgeRepositoryInternal {
    Flux<Badge> findAllBy(Pageable pageable);

    @Override
    <S extends Badge> Mono<S> save(S entity);

    @Override
    Flux<Badge> findAll();

    @Override
    Mono<Badge> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);

    @Override
    Flux<Badge> findAllBadgesOfGym(Long gym_id, Pageable pageable);

    @Override
    Mono<Long> countFindAllBadgesOfGym(Long gym_id);

    @Override
    Mono<Badge> findOneBySession(String email);
    //    Mono<Long> countFindAllBadgesOfGym(Long gym_id);
    //    @Query("SELECT * " +
    //        "FROM badge entity " +
    //        "WHERE entity.gym_id  = :gym_id " +
    //        "limit :limit " +
    //        "offset :offset * :limit"
    //    )
    //    Flux<Badge> findAllBadgesOfGym(Long gym_id, Integer offset, Integer limit);
    //
    //    @Query("SELECT count(*) " +
    //        "FROM badge entity " +
    //        "WHERE entity.gym_id  = :gym_id "
    //    )
    //    Mono<Long> countFindAllBadgesOfGym(Long gym_id);
}

interface BadgeRepositoryInternal {
    <S extends Badge> Mono<S> save(S entity);

    Flux<Badge> findAllBy(Pageable pageable);

    Flux<Badge> findAll();

    Mono<Badge> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Badge> findAllBy(Pageable pageable, Criteria criteria);

    Flux<Badge> findAllBadgesOfGym(Long gym_id, Pageable pageable);

    Mono<Long> countFindAllBadgesOfGym(Long gym_id);
    Mono<Badge> findOneBySession(String email);
}
