package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.alphalab.domain.Gym} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RelCriteriaProgressDTO extends AbstractAuditingEntity<Long> implements Serializable {

    private Long id;
    private Double value;

    private Long criteriaId;

    private Long progressId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Long getCriteriaId() {
        return criteriaId;
    }

    public void setCriteriaId(Long criteriaId) {
        this.criteriaId = criteriaId;
    }

    public Long getProgressId() {
        return progressId;
    }

    public void setProgressId(Long progressId) {
        this.progressId = progressId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelCriteriaProgressDTO)) {
            return false;
        }

        RelCriteriaProgressDTO gymDTO = (RelCriteriaProgressDTO) o;
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
            "RelCriteriaProgressDTO{" + "id=" + id + ", value=" + value + ", criteriaId=" + criteriaId + ", progressId=" + progressId + '}'
        );
    }
}
