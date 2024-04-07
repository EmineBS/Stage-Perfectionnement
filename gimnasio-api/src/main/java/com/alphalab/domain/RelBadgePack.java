package com.alphalab.domain;

import com.alphalab.domain.enumeration.BadgePackStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Badge.
 */
@Table("rel_badge_pack")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RelBadgePack extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("enabled")
    private Boolean enabled;

    @Column("status")
    private BadgePackStatus status;

    @Transient
    @JsonIgnoreProperties(value = { "badges" }, allowSetters = true)
    private Badge badge;

    @Transient
    @JsonIgnoreProperties(value = { "extBadgesDesignations" }, allowSetters = true)
    private ExtBadgeDesignation extBadgesDesignation;

    @Transient
    @JsonIgnoreProperties(value = { "packs" }, allowSetters = true)
    private OffrePack pack;

    @Column("badge_id")
    private Long badgeId;

    @Column("pack_id")
    private Long packId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RelBadgePack id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public RelBadgePack enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public BadgePackStatus getStatus() {
        return status;
    }

    public RelBadgePack status(BadgePackStatus status) {
        this.status(status);
        return this;
    }

    public void setStatus(BadgePackStatus status) {
        this.status = status;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
        this.badgeId = badge != null ? badge.getId() : null;
    }

    public RelBadgePack badge(Badge badge) {
        this.setBadge(badge);
        return this;
    }

    public OffrePack getPack() {
        return pack;
    }

    public void setPack(OffrePack pack) {
        this.pack = pack;
        this.packId = pack != null ? pack.getId() : null;
    }

    public RelBadgePack pack(OffrePack pack) {
        this.setPack(pack);
        return this;
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

    public Long getPackId() {
        return packId;
    }

    public void setPackId(Long packId) {
        this.packId = packId;
    }

    public ExtBadgeDesignation getExtBadgesDesignation() {
        return extBadgesDesignation;
    }

    public void setExtBadgesDesignation(ExtBadgeDesignation extBadgesDesignation) {
        this.extBadgesDesignation = extBadgesDesignation;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelBadgePack)) {
            return false;
        }
        return id != null && id.equals(((RelBadgePack) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "RelBadgePack{" +
            "id=" +
            id +
            ", enabled=" +
            enabled +
            ", status=" +
            status +
            ", badge=" +
            badge +
            ", extBadgesDesignation=" +
            extBadgesDesignation +
            ", pack=" +
            pack +
            ", badgeId=" +
            badgeId +
            ", packId=" +
            packId +
            '}'
        );
    }
}
