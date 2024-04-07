package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.alphalab.domain.Progress} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProgressDTO extends AbstractAuditingEntity<Long> implements Serializable {

    private Long id;
    private Long badgeId;

    public Long getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProgressDTO)) {
            return false;
        }

        ProgressDTO progressDTO = (ProgressDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, progressDTO.id);
    }

    @Override
    public String toString() {
        return "ProgressDTO{" + "id=" + id + ", badgeId=" + badgeId + '}';
    }
}
