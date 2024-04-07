package com.alphalab.repository;

import com.alphalab.domain.OffrePack;
import com.alphalab.domain.RDV;
import java.time.Instant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the RDV entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RDVRepository extends ReactiveCrudRepository<RDV, Long>, RDVRepositoryInternal {
    Flux<RDV> findAllBy(Pageable pageable);

    @Override
    <S extends RDV> Mono<S> save(S entity);

    @Override
    Flux<RDV> findAll();

    @Override
    Mono<RDV> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);

    @Override
    Flux<RDV> findAllByBadge(Long badge_id, Pageable pageable);

    @Override
    Mono<Long> countAllByBadge(Long badge_id);

    @Override
    Mono<Long> countAllByBadgeAndStatusIsConfirmed(Long badge_id);

    @Query(
        "SELECT * " +
        "FROM rdv entity " +
        "join rel_badge_pack rbp " +
        "on entity.rel_badge_pack_id = rbp.id " +
        "where rbp.badge_id = :badge_id " +
        "and entity.status = 'CONFIRMED' " +
        "and Date(entity.from_date) = CURRENT_DATE "
    )
    Mono<RDV> findRDVBookedToday(Long badge_id);

    @Query(
        "SELECT count(*) " +
        "FROM rdv entity " +
        "join rel_badge_pack rbp " +
        "on entity.rel_badge_pack_id = rbp.id " +
        "where rbp.badge_id = :badge_id " +
        "and entity.status = 'CONFIRMED' " +
        "and Date(entity.from_date) = Date(:from_date) "
    )
    Mono<Long> checkIfThereIsAlreadyBookedVisitAtAGivenDate(Long badge_id, Instant from_date);

    @Override
    Flux<RDV> findAllByGym(Long gym_id, Pageable pageable);

    @Override
    Mono<Long> countAllByGym(Long gym_id);

    //    @Query("select entity.*  " +
    //        "from rdv entity " +
    //        "join rel_badge_pack rbp " +
    //        "on entity.rel_badge_pack_id = rbp.id " +
    //        "join badge b " +
    //        "on rbp.badge_id = b.id " +
    //        "join profile p " +
    //        "on b.id = p.badge_id " +
    //        "where p.email = :userId "
    //    )
    @Override
    Flux<RDV> findAllOfCurrentBadgeAccount(String userId);

    @Override
    Flux<RDV> findAllExceptOfCurrentBadgeAccount(String userId);

    @Query(
        "select entity.*  " +
        "from rdv entity " +
        "join calendar c " +
        "on c.id = entity.calendar_id " +
        "join gym g " +
        "on c.gym_id = g.id " +
        "join badge b " +
        "on g.id = b.gym_id " +
        "join profile p " +
        "on b.id = p.badge_id " +
        "where p.email = :userId "
    )
    Flux<RDV> findAllOfCalendarBadge(String userId);
}

interface RDVRepositoryInternal {
    <S extends RDV> Mono<S> save(S entity);

    Flux<RDV> findAllBy(Pageable pageable);

    Flux<RDV> findAll();

    Mono<RDV> findById(Long id);

    Flux<RDV> findAllByBadge(Long badge_id, Pageable pageable);

    Mono<Long> countAllByBadge(Long badge_id);

    Flux<RDV> findAllByGym(Long gym_id, Pageable pageable);

    Mono<Long> countAllByGym(Long gym_id);
    Flux<RDV> findAllOfCurrentBadgeAccount(String userId);
    Flux<RDV> findAllExceptOfCurrentBadgeAccount(String userId);

    Mono<Long> countAllByBadgeAndStatusIsConfirmed(Long badge_id);
}
