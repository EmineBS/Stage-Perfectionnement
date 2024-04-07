package com.alphalab.repository;

import com.alphalab.domain.RelGymFeature;
import com.alphalab.domain.RelUserGym;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the RelGymFeature entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RelGymFeatureRepository extends ReactiveCrudRepository<RelGymFeature, Long>, RelGymFeatureRepositoryInternal {
    Flux<RelGymFeature> findAllBy(Pageable pageable);

    @Override
    <S extends RelGymFeature> Mono<S> save(S entity);

    @Override
    Flux<RelGymFeature> findAll();

    @Override
    Mono<RelGymFeature> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);

    @Override
    Mono<RelGymFeature> findRelOfGymWhereFeatureIsBadgeDesignation(Long gym_id);

/*    @Query("SELECT * " + "FROM rel_gym_feature entity " + "WHERE entity.gym_id  = :gym_id " + "limit :limit " + "offset :offset * :limit")
    Flux<RelGymFeature> findFeaturesByGym(Long gym_id, Integer offset, Integer limit);

    @Query("SELECT count(*) " + "FROM rel_gym_feature entity " + "WHERE entity.gym_id  = :gym_id ")
    Mono<Long> countFeaturesByGym(Long gym_id);*/
    @Override
    Flux<RelGymFeature> findFeaturesByGym(Pageable pageable, Long gym_id);
    @Override
    Mono<Long> countFeaturesByGym(Long gym_id);
    @Override
    Mono<RelGymFeature> findRelByFeatureAndGym(Long gym_id, Long feature_id);

}

interface RelGymFeatureRepositoryInternal {
    <S extends RelGymFeature> Mono<S> save(S entity);

    Flux<RelGymFeature> findAllBy(Pageable pageable);

    Flux<RelGymFeature> findAll();

    Mono<RelGymFeature> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<RelGymFeature> findAllBy(Pageable pageable, Criteria criteria);

    Mono<RelGymFeature> findRelOfGymWhereFeatureIsBadgeDesignation(Long gym_id);
    Flux<RelGymFeature> findFeaturesByGym(Pageable pageable, Long gym_id);
    Mono<Long> countFeaturesByGym(Long gym_id);

    Mono<RelGymFeature> findRelByFeatureAndGym(Long gym_id, Long feature_id);
}
