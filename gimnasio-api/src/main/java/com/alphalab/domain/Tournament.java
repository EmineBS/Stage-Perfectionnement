package com.alphalab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Pack;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Badge.
 */
@Table("tournament")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Tournament extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("registration")
    private Boolean registration;

    @Column("starttimestamp")
    private Number starttimestamp;

    @Column("minplayers")
    private Number minplayers;

    @Column("maxplayers")
    private Number maxplayers;

    @Column("gameid")
    private Long gameid;

    @Transient
    @JsonIgnoreProperties(value = { "tournaments" }, allowSetters = true)
    private Game game;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Tournament id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Tournament name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getRegistration() {
        return registration;
    }

    public Tournament registration(Boolean registration) {
        this.setRegistration(registration);
        return this;
    }

    public void setRegistration(Boolean registration) {
        this.registration = registration;
    }

    //--

    public Number getStarttimestamp() {
        return starttimestamp;
    }

    public Tournament starttimestamp(Number starttimestamp) {
        this.setStarttimestamp(starttimestamp);
        return this;
    }

    public void setStarttimestamp(Number starttimestamp) {
        this.starttimestamp = starttimestamp;
    }

    //--

    public Number getMinplayers() {
        return minplayers;
    }

    public Tournament minplayers(Number minplayers) {
        this.setMinplayers(minplayers);
        return this;
    }

    public void setMinplayers(Number minplayers) {
        this.minplayers = minplayers;
    }

    //--

    public Number getMaxplayers() {
        return maxplayers;
    }

    public Tournament maxplayers(Number maxplayers) {
        this.setMaxplayers(maxplayers);
        return this;
    }

    public void setMaxplayers(Number maxplayers) {
        this.maxplayers = maxplayers;
    }

    //--

    public Long getGameId() {
        return gameid;
    }

    public void setGameId(Long gameid) {
        this.gameid = gameid;
    }

    public void setGame(Game game) {
        this.game = game;
        this.gameid = game != null ? game.getId() : null;
    }

    public Tournament game(Game game) {
        this.game(game);
        return this;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Tournament)) {
            return false;
        }
        return id != null && id.equals(((Tournament) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "Tournament{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", registration='" + registration + '\'' +
            ", starttimestamp='" + starttimestamp + '\'' +
            ", minplayers='" + minplayers + '\'' +
            ", maxplayers='" + maxplayers + '\'' +
            ", gameid=" + gameid +
            ", game=" + game +
            '}';
    }
}
