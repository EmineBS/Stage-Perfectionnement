package com.alphalab.repository;

import com.alphalab.domain.Gym;
import com.alphalab.domain.OffrePack;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the OffrePack entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OffrePackRepository extends ReactiveCrudRepository<OffrePack, Long>, OffrePackRepositoryInternal {
    Flux<OffrePack> findAllBy(Pageable pageable);

    @Override
    <S extends OffrePack> Mono<S> save(S entity);

    @Override
    Flux<OffrePack> findAll();

    @Override
    Mono<OffrePack> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);

    @Query(
        "SELECT * " +
        "FROM pack entity " +
        "WHERE entity.gym_id  = :gym_id " +
        "order by entity.id desc " +
        "limit :limit " +
        "offset :offset * :limit"
    )
    Flux<OffrePack> findAllPacksByGym(Long gym_id, Integer offset, Integer limit);

    @Query("SELECT count(*) " + "FROM pack entity " + "WHERE entity.gym_id  = :gym_id ")
    Mono<Long> countAllPacksByGym(Long gym_id);

    @Query(
        "SELECT * " +
        "FROM pack entity " +
        "join rel_badge_pack rbp " +
        "on entity.id = rbp.pack_id " +
        "join badge b " +
        "on rbp.badge_id = b.id " +
        "where rbp.status = 'ACTIVE' " +
        "and rbp.enabled  = 'true' " +
        "and b.uid = :badge_uid " +
        "limit 1 "
    )
    Mono<OffrePack> findPackByBadge(String badge_uid);
}

interface OffrePackRepositoryInternal {
    <S extends OffrePack> Mono<S> save(S entity);

    Flux<OffrePack> findAllBy(Pageable pageable);

    Flux<OffrePack> findAll();

    Mono<OffrePack> findById(Long id);
    // this is not supported at the moment because of https://github.com/jhipster/generator-jhipster/issues/18269
    // Flux<OffrePack> findAllBy(Pageable pageable, Criteria criteria);

}
