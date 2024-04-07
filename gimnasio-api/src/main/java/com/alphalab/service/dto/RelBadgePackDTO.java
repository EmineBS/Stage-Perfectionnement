package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;
import com.alphalab.domain.Badge;
import com.alphalab.domain.OffrePack;
import com.alphalab.domain.enumeration.BadgePackStatus;
import com.alphalab.domain.enumeration.GymStatus;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.alphalab.domain.Gym} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RelBadgePackDTO extends AbstractAuditingEntity<Long> implements Serializable {

    private Long id;

    private Boolean enabled = Boolean.FALSE;

    private BadgePackStatus status = BadgePackStatus.ACTIVE;

    private OffrePack pack;

    private Badge badge;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public BadgePackStatus getStatus() {
        return status;
    }

    public void setStatus(BadgePackStatus status) {
        this.status = status;
    }

    public OffrePack getPack() {
        return pack;
    }

    public void setPack(OffrePack pack) {
        this.pack = pack;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelBadgePackDTO)) {
            return false;
        }

        RelBadgePackDTO gymDTO = (RelBadgePackDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, gymDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "RelBadgePackDTO{" +
            "id=" + id +
            ", enabled='" + enabled + '\'' +
            ", status=" + status +
            ", pack=" + pack +
            ", badge=" + badge +
            '}';
    }
}
