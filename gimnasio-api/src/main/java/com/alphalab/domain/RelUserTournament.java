package com.alphalab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Badge.
 */
@Table("rel_user_tournament")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RelUserTournament extends AbstractAuditingEntity<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Transient
    @JsonIgnoreProperties(value = { "users" }, allowSetters = true)
    private User user;

    @Transient
    @JsonIgnoreProperties(value = { "tournaments" }, allowSetters = true)
    private Tournament tournament;

    @Column("user_id")
    private String userId;

    @Column("tournament_id")
    private Long tournamentId;

    public Long getId() {
        return this.id;
    }

    public RelUserTournament id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
        this.tournamentId = tournament != null ? tournament.getId() : null;
    }

    public RelUserTournament tournament(Tournament tournament) {
        this.setTournament(tournament);
        return this;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.userId = user != null ? user.getId() : null;
    }

    public RelUserTournament user(User user) {
        this.setUser(user);
        return this;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelUserTournament)) {
            return false;
        }
        return id != null && id.equals(((RelUserTournament) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "RelUserTournamentDTO{" +
            "id=" + id +
            ", user=" + user +
            ", tournament=" + tournament +
            ", userId=" + userId +
            ", tournamentId=" + tournamentId +
            '}';
    }
}
