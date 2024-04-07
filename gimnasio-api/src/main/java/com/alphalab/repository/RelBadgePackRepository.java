package com.alphalab.repository;

import com.alphalab.domain.OffrePack;
import com.alphalab.domain.RelBadgePack;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the RelBadgePack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelBadgePackRepository extends ReactiveCrudRepository<RelBadgePack, Long>, RelBadgePackRepositoryInternal {
    Flux<RelBadgePack> findAllBy(Pageable pageable);

    @Override
    <S extends RelBadgePack> Mono<S> save(S entity);

    @Override
    Flux<RelBadgePack> findAll();

    @Override
    Mono<RelBadgePack> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);

    @Override
    Flux<RelBadgePack> findAllPacksByBadge(Long badge_id, Pageable pageable);

    @Override
    Flux<RelBadgePack> findRelBadgePackByGym(Long gym_id, Pageable pageable);

    @Override
    Mono<Long> countAllPacksByBadge(Long badge_id);

    @Override
    Flux<RelBadgePack> findAllBadgesWithOrWithoutPack(Long gym_id, Pageable pageable);

    @Override
    Mono<Long> countAllBadgesWithOrWithoutPack(Long gym_id);

    @Query(
        "SELECT * " + "FROM rel_badge_pack entity " + "where entity.status = 'ACTIVE' " + "and entity.badge_id  = :badge_id " + "limit 1 "
    )
    Mono<RelBadgePack> findRelBadgePackWherePackIsActive(Long badge_id);

    @Override
    Mono<RelBadgePack> findRelBadgePackByBadge(Long badge_id);

    @Override
    Mono<RelBadgePack> findRelOfBadgeWhereStatusIsActive(Long badge_id);
}

interface RelBadgePackRepositoryInternal {
    <S extends RelBadgePack> Mono<S> save(S entity);

    Flux<RelBadgePack> findAllBy(Pageable pageable);

    Flux<RelBadgePack> findAll();

    Mono<RelBadgePack> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<RelBadgePack> findAllBy(Pageable pageable, Criteria criteria);

    Flux<RelBadgePack> findAllPacksByBadge(Long badge_id, Pageable pageable);

    Mono<Long> countAllPacksByBadge(Long badge_id);

    Mono<RelBadgePack> findActiveByRelBadgePackId(String badge_uid);

    Flux<RelBadgePack> findRelBadgePackByGym(Long badge_id, Pageable pageable);

    Flux<RelBadgePack> findAllBadgesWithOrWithoutPack(Long gym_id, Pageable pageable);
    Mono<Long> countAllBadgesWithOrWithoutPack(Long gym_id);

    Mono<RelBadgePack> findRelBadgePackByBadge(Long badge_id);
    Mono<RelBadgePack> findRelOfBadgeWhereStatusIsActive(Long badge_id);
}
