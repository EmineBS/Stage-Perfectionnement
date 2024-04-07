package com.alphalab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Progress.
 */
@Table("progress")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Progress extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("badge_id")
    private Long badgeId;

    @Transient
    @JsonIgnoreProperties(value = { "badges" }, allowSetters = true)
    private Badge badge;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Progress id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

    public Badge getBadge() {
        return badge;
    }

    public Progress badge(Badge badge) {
        this.setBadge(badge);
        return this;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
        this.badgeId = badge != null ? badge.getId() : null;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Progress)) {
            return false;
        }
        return id != null && id.equals(((Progress) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Progress{" + "id=" + id + ", badgeId=" + badgeId + ", badge=" + badge + '}';
    }
}
