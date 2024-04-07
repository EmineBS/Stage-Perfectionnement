package com.alphalab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Badge.
 */
@Table("rel_gym_feature")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RelGymFeature extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Transient
    @JsonIgnoreProperties(value = { "gyms" }, allowSetters = true)
    private Gym gym;

    @Transient
    @JsonIgnoreProperties(value = { "features" }, allowSetters = true)
    private Feature feature;

    @Column("gym_id")
    private Long gymId;

    @Column("feature_id")
    private Long featureId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RelGymFeature id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Feature getFeature() {
        return feature;
    }

    public void setFeature(Feature feature) {
        this.feature = feature;
        this.featureId = feature != null ? feature.getId() : null;
    }

    public RelGymFeature feature(Feature feature) {
        this.setFeature(feature);
        return this;
    }

    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
        this.gymId = gym != null ? gym.getId() : null;
    }

    public RelGymFeature gym(Gym gym) {
        this.setGym(gym);
        return this;
    }

    public Long getFeatureId() {
        return featureId;
    }

    public void setFeatureId(Long featureId) {
        this.featureId = featureId;
    }

    public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelGymFeature)) {
            return false;
        }
        return id != null && id.equals(((RelGymFeature) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "RelGymFeature{" + "id=" + id + ", gym=" + gym + ", feature=" + feature + ", gymId=" + gymId + ", featureId=" + featureId + '}'
        );
    }
}
