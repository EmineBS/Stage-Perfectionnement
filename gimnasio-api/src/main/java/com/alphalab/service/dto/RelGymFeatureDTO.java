package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;
import com.alphalab.domain.RelUserGym;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link RelUserGym} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RelGymFeatureDTO extends AbstractAuditingEntity<Long> implements Serializable {

    private Long id;

    private Long gymId;

    private Long featureId;

    private String featureName;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public String getFeatureName() {
        return featureName;
    }

    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelGymFeatureDTO)) {
            return false;
        }

        RelGymFeatureDTO gymDTO = (RelGymFeatureDTO) o;
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
        return "RelGymFeatureDTO{" +
            "id=" + id +
            ", gymId=" + gymId +
            ", featureId=" + featureId +
            ", featureName='" + featureName + '\'' +
            '}';
    }
}
