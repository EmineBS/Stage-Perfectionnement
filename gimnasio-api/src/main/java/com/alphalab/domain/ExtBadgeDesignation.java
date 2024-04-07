package com.alphalab.domain;

import com.alphalab.domain.enumeration.BadgeStatus;
import com.alphalab.domain.enumeration.ExtBadgeDesignationStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Badge.
 */
@Table("ext_badge_designation")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExtBadgeDesignation extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("status")
    private ExtBadgeDesignationStatus status;

    @Column("badge_id")
    private Long badgeId;

    @Column("user_id")
    private String userId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ExtBadgeDesignation id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExtBadgeDesignationStatus getStatus() {
        return status;
    }

    public ExtBadgeDesignation status(ExtBadgeDesignationStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(ExtBadgeDesignationStatus status) {
        this.status = status;
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public ExtBadgeDesignation badgeId(Long badgeId) {
        this.setBadgeId(badgeId);
        return this;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

    public String getUserId() {
        return userId;
    }

    public ExtBadgeDesignation userId(String userId) {
        this.setUserId(userId);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ExtBadgeDesignation)) {
            return false;
        }
        return id != null && id.equals(((ExtBadgeDesignation) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "ExtBadgeDesignation{" +
            "id=" + id +
            ", status='" + status + '\'' +
            ", badgeId=" + badgeId +
            ", userId='" + userId + '\'' +
            '}';
    }
}
