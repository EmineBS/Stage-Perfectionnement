package com.alphalab.repository;

import com.alphalab.domain.CheckIn;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Checkin entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckinRepository extends ReactiveCrudRepository<CheckIn, Long>, CheckinRepositoryInternal {
    Flux<CheckIn> findAllBy(Pageable pageable);

    @Override
    <S extends CheckIn> Mono<S> save(S entity);

    @Override
    Flux<CheckIn> findAll();

    @Override
    Mono<CheckIn> findById(Long id);

    @Override
    Flux<CheckIn> findAllByUserId(Pageable pageable, String userId);

    @Override
    Mono<Void> deleteById(Long id);

    Flux<CheckIn> findAllByBadgeUID(Pageable pageable, String badge_uid);

    @Override
    Mono<Long> countAllByBadgeUID(String badge_uid);

    @Override
    Flux<CheckIn> findAllByGym(Pageable pageable, Long gym_id);

    @Override
    Mono<Long> countAllByGym(Long gym_id);

    @Query(
        "SELECT count(*) " +
        "FROM checkin entity " +
        "join badge b " +
        "on entity.badge_uid = b.uid " +
        "join rel_badge_pack rbp " +
        "on b.id = rbp.badge_id " +
        "join pack p " +
        "on rbp.pack_id = p.id " +
        "WHERE entity.status = 'CONFIRMED' " +
        "and rbp.pack_id = :pack_id " +
        "and entity.badge_uid  = :badge_uid "
    )
    Mono<Long> countAllCheckinOfBadge(String badge_uid, Long pack_id);

    @Query("SELECT count(*) " + "FROM checkin entity " + "WHERE entity.user_id = :user_id ")
    Mono<Long> countAllCheckinByUser(String userId);

    @Query(
        "SELECT count(*) " +
        "FROM checkin entity " +
        "WHERE entity.status = 'CONFIRMED' " +
        "and entity.rel_badge_pack_id  = :relBadgePackId "
    )
    Mono<Long> countAllCheckinOfRelBadgePack(Long relBadgePackId);
}

interface CheckinRepositoryInternal {
    <S extends CheckIn> Mono<S> save(S entity);

    Flux<CheckIn> findAllBy(Pageable pageable);

    Flux<CheckIn> findAll();

    Flux<CheckIn> findAllByUserId(Pageable pageable, String userId);

    Mono<CheckIn> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Checkin> findAllBy(Pageable pageable, Criteria criteria);

    Flux<CheckIn> findAllByBadgeUID(Pageable pageable, String badge_uid);
    Mono<Long> countAllByBadgeUID(String badge_uid);

    Flux<CheckIn> findAllByGym(Pageable pageable, Long gym_id);
    Mono<Long> countAllByGym(Long gym_id);
}
