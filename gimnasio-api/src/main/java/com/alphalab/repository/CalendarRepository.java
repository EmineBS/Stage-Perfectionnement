package com.alphalab.repository;

import com.alphalab.domain.Calendar;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Spring Data R2DBC repository for the Calendar entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CalendarRepository extends ReactiveCrudRepository<Calendar, Long>, CalendarRepositoryInternal {
    Flux<Calendar> findAllBy(Pageable pageable);

    @Override
    <S extends Calendar> Mono<S> save(S entity);

    @Override
    Flux<Calendar> findAll();

    @Override
    Mono<Calendar> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);

    @Override
    Mono<Calendar> findCalendarByGym(Long gym_id);

    @Query(
        "select entity.* " +
        "from calendar entity " +
        "join gym g " +
        "on entity.gym_id = g.id " +
        "join badge b " +
        "on g.id = b.gym_id " +
        "join profile p " +
        "on b.id = p.badge_id " +
        "where p.email = :userId"
    )
    Mono<Calendar> findOneOfCurrentBadgeSession(String userId);
}

interface CalendarRepositoryInternal {
    <S extends Calendar> Mono<S> save(S entity);

    Flux<Calendar> findAllBy(Pageable pageable);

    Flux<Calendar> findAll();

    Mono<Calendar> findById(Long id);
    Mono<Calendar> findCalendarByGym(Long gym_id);
}
