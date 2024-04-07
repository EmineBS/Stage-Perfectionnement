package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;
import com.alphalab.domain.enumeration.BadgeStatus;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.alphalab.domain.Badge} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class BadgeDTO extends AbstractAuditingEntity<Long> implements Serializable {

    private Long id;
    private String uid;

    private BadgeStatus status = BadgeStatus.ACTIVATED;

    private Boolean enabled = true;
    private Long gymId;

    private String gymName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    public BadgeStatus getStatus() {
        return status;
    }

    public void setStatus(BadgeStatus status) {
        this.status = status;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BadgeDTO)) {
            return false;
        }

        BadgeDTO gymDTO = (BadgeDTO) o;
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
            "BadgeDTO{" +
            "id=" +
            id +
            ", uid='" +
            uid +
            '\'' +
            ", status='" +
            status +
            '\'' +
            ", gym_id=" +
            gymId +
            ", gym_name=" +
            gymName +
            '}'
        );
    }
}
