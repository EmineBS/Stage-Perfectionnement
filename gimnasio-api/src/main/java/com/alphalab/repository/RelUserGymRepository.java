package com.alphalab.repository;

import com.alphalab.domain.Gym;
import com.alphalab.domain.RelBadgePack;
import com.alphalab.domain.RelUserGym;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the RelUserGym entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelUserGymRepository extends ReactiveCrudRepository<RelUserGym, Long>, RelUserGymRepositoryInternal {
    Flux<RelUserGym> findAllBy(Pageable pageable);

    @Override
    <S extends RelUserGym> Mono<S> save(S entity);

    @Override
    Flux<RelUserGym> findAll();

    @Override
    Mono<RelUserGym> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);

    @Query("SELECT * " + "FROM rel_user_gym entity " + "where entity.user_id = :user_id " + "and entity.gym_id  = :gym_id " + "limit 1 ")
    Mono<RelUserGym> findRelUserGym(String user_id, Long gym_id);

    //find gym by badge
    @Query("SELECT * " + "FROM rel_user_gym entity " + "WHERE entity.gym_id  = :gym_id " + "limit :limit " + "offset :offset * :limit")
    Flux<RelUserGym> findUsersByGym(Long gym_id, Integer offset, Integer limit);

    @Query("SELECT count(*) " + "FROM rel_user_gym entity " + "WHERE entity.gym_id  = :gym_id ")
    Mono<Long> countUsersByGym(Long gym_id);
}

interface RelUserGymRepositoryInternal {
    <S extends RelUserGym> Mono<S> save(S entity);

    Flux<RelUserGym> findAllBy(Pageable pageable);

    Flux<RelUserGym> findAll();

    Mono<RelUserGym> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<RelUserGym> findAllBy(Pageable pageable, Criteria criteria);
}
