package com.alphalab.repository;

import com.alphalab.domain.RelUserTournament;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 */
@SuppressWarnings("unused")
@Repository
public interface RelUserTournamentRepository extends ReactiveCrudRepository<RelUserTournament, Long>, RelUserTournamentRepositoryInternal {
    Flux<RelUserTournament> findAllBy(Pageable pageable);

    @Override
    <S extends RelUserTournament> Mono<S> save(S entity);

    @Override
    Flux<RelUserTournament> findAll();

    @Override
    Mono<RelUserTournament> findById(Long id);

    @Override
    Mono<Void> deleteById(Long id);

    @Query("SELECT * " + "FROM rel_user_tournament entity " + "where entity.user_id = :user_id " + "and entity.tournament_id  = :tournament_id " + "limit 1 ")
    Mono<RelUserTournament> findRelUserTournament(String user_id, Long tournament_id);
    @Query("SELECT * " + "FROM rel_user_tournament entity " + "WHERE entity.tournament_id  = :tournament_id " + "limit :limit " + "offset :offset * :limit")
    Flux<RelUserTournament> findUsersByTournament(Long tournament_id, Integer offset, Integer limit);
    @Query("SELECT count(*) " + "FROM rel_user_tournament entity " + "WHERE entity.tournament_id  = :tournament_id ")
    Mono<Long> countUsersByTournament(Long tournament_id);

}

interface RelUserTournamentRepositoryInternal {
    <S extends RelUserTournament> Mono<S> save(S entity);

    Flux<RelUserTournament> findAllBy(Pageable pageable);

    Flux<RelUserTournament> findAll();

    Mono<RelUserTournament> findById(Long id);
}
