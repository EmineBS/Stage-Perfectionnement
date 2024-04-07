package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;

import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class RelUserTournamentDTO extends AbstractAuditingEntity<Long> implements Serializable {
    private Long id;

    private Long tournamentId;

    private String userId;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        if (!(o instanceof RelUserTournamentDTO)) {
            return false;
        }

        RelUserTournamentDTO tournamentDTO = (RelUserTournamentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, tournamentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "RelUserTournamentDTO{" +
            "id=" + id +
            ", tournamentId=" + tournamentId +
            ", userId='" + userId + '\'' +
            '}';
    }
}
