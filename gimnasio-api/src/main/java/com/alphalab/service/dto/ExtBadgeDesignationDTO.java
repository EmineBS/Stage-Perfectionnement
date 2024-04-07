package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;
import com.alphalab.domain.enumeration.BadgeStatus;
import com.alphalab.domain.enumeration.ExtBadgeDesignationStatus;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.alphalab.domain.Gym} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ExtBadgeDesignationDTO extends AbstractAuditingEntity<Long> implements Serializable {

    private Long id;
    private ExtBadgeDesignationStatus status;

    private Long badgeId;
    private String userId;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ExtBadgeDesignationStatus getStatus() {
        return status;
    }

    public void setStatus(ExtBadgeDesignationStatus status) {
        this.status = status;
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
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
        if (!(o instanceof ExtBadgeDesignationDTO)) {
            return false;
        }

        ExtBadgeDesignationDTO gymDTO = (ExtBadgeDesignationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, gymDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "ExtBadgeDesignationDTO{" +
            "id=" +
            id +
            ", status='" +
            status +
            '\'' +
            ", badgeId=" +
            badgeId +
            ", userId='" +
            userId +
            '\'' +
            '}'
        );
    }
}
