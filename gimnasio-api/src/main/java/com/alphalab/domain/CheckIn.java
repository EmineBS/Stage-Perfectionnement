package com.alphalab.domain;

import com.alphalab.domain.enumeration.CheckinStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Badge.
 */
@Table("checkin")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CheckIn extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("status")
    private CheckinStatus status;

    @Column("user_id")
    private String userId;

    @Column("rel_badge_pack_id")
    private Long relBadgePackId;

    @Transient
    @JsonIgnoreProperties(value = { "relBadgePack" }, allowSetters = true)
    private RelBadgePack relBadgePack;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public CheckIn id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CheckinStatus getStatus() {
        return status;
    }

    public CheckIn status(CheckinStatus status) {
        this.status(status);
        return this;
    }

    public void setStatus(CheckinStatus status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public CheckIn userId(String id) {
        this.setUserId(id);
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckIn)) {
            return false;
        }
        return id != null && id.equals(((CheckIn) o).id);
    }

    public CheckIn() {}

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    public Long getRelBadgePackId() {
        return relBadgePackId;
    }

    public void setRelBadgePackId(Long relBadgePackId) {
        this.relBadgePackId = relBadgePackId;
    }

    public RelBadgePack getRelBadgePack() {
        return relBadgePack;
    }

    public void setRelBadgePack(RelBadgePack relBadgePack) {
        this.relBadgePack = relBadgePack;
    }

    @Override
    public String toString() {
        return (
            "CheckIn [id=" +
            id +
            ", status=" +
            status +
            ", userId=" +
            userId +
            ", relBadgePackId=" +
            relBadgePackId +
            ", relBadgePack=" +
            relBadgePack +
            "]"
        );
    }
}
