package com.alphalab.repository;

import com.alphalab.domain.Gym;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Gym entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GymRepository extends ReactiveCrudRepository<Gym, Long>, GymRepositoryInternal {
    Flux<Gym> findAllBy(Pageable pageable);

    @Override
    <S extends Gym> Mono<S> save(S entity);

    @Override
    Flux<Gym> findAll();

    @Override
    Mono<Gym> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);

    //    @Override
    //    Flux<Gym> findAllGymsByUser(String id_user, Pageable pageable);
    //    @Override
    //    Mono<Long> countAllGymsByUser(String id_user);

    @Query(
        "SELECT entity.* " +
        "FROM gym entity " +
        "JOIN rel_user_gym rug " +
        "ON entity.id = rug.gym_id " +
        "WHERE rug.user_id  = :id " +
        "order by entity.id desc " +
        "limit :limit " +
        "offset :offset * :limit"
    )
    Flux<Gym> findAllGymsByUser(String id, Integer offset, Integer limit);

    @Query("SELECT count(*) " + "FROM gym entity " + "JOIN rel_user_gym rug " + "ON entity.id = rug.gym_id " + "WHERE rug.user_id  = :id ")
    Mono<Long> countAllGymsByUser(String id);

    @Query("SELECT * " + "FROM gym entity " + "JOIN badge b " + "ON entity.id = b.gym_id " + "WHERE b.uid  = :uid " + "limit 1 ")
    Mono<Gym> findGymByBadgeUID(String uid);

    //find gym by badge

    @Query(
        "select g.*, string_agg(rug.user_id::text, ', ')  as users " +
        "from gym g " +
        "join rel_user_gym rug on g.id = rug.gym_id " +
        "where g.id = :gym_id " +
        "group by g.id"
    )
    Flux<String> findGymWithUsers(Long gym_id);

    @Query(
        "select g.* " +
        "from gym g " +
        "join badge b " +
        "on g.id = b.gym_id " +
        "join ext_badge_designation ebd " +
        "on b.id = ebd.badge_id " +
        "where ebd.user_id = :user_id " +
        "limit 1"
    )
    Mono<Gym> findGymByCurrentBadgeAccount(String user_id);
}

interface GymRepositoryInternal {
    <S extends Gym> Mono<S> save(S entity);

    Flux<Gym> findAllBy(Pageable pageable);

    Flux<Gym> findAll();

    Mono<Gym> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<Gym> findAllBy(Pageable pageable, Criteria criteria);
    //    Flux<Gym> findAllGymsByUser(String id_user, Pageable pageable);
    //    Mono<Long> countAllGymsByUser(String id_user);
}
