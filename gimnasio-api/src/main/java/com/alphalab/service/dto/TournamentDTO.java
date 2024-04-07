package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;
import java.io.Serializable;
import java.util.Objects;

@SuppressWarnings("common-java:DuplicatedBlocks")
public class TournamentDTO extends AbstractAuditingEntity<Long> implements Serializable {

    private Long id;
    private String name;

    private Boolean registration = true;

    private Number starttimestamp;
    private Number minplayers;
    private Number maxplayers;

    private Long gameid;
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRegistration() {
        return registration;
    }

    public void setRegistration(Boolean registration) {
        this.registration = registration;
    }

    public Number getStarttimestamp() {
        return starttimestamp;
    }

    public void setStarttimestamp(Number starttimestamp) {
        this.starttimestamp = starttimestamp;
    }

    public Number getMinplayers() {
        return minplayers;
    }

    public void setMinplayers(Number minplayers) {
        this.minplayers = minplayers;
    }

    public Number getMaxplayers() {
        return maxplayers;
    }

    public void setMaxplayers(Number maxplayers) {
        this.maxplayers = maxplayers;
    }

    public Long getGameId() {
        return gameid;
    }

    public void setGameId(Long gameid) {
        this.gameid = gameid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TournamentDTO)) {
            return false;
        }

        TournamentDTO gameDTO = (TournamentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, gameDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "TournamentDTO{" +
            "id=" +
            id +
            ", name='" +
            name +
            '\'' +
            ", registration='" +
            registration +
            '\'' +
            ", starttimestamp='" +
            starttimestamp +
            '\'' +
            ", minplayers='" +
            minplayers +
            '\'' +
            ", maxplayers='" +
            maxplayers +
            '\'' +
            ", gameid=" +
            gameid +
            '}'
        );
    }
}
