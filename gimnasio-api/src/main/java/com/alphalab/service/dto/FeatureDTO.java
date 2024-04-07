package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;
import com.alphalab.domain.enumeration.FeatureName;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.alphalab.domain.Gym} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FeatureDTO extends AbstractAuditingEntity<Long> implements Serializable {

    private Long id;

    private String name;
    private String description;
    private Boolean enabled = false;
    private Double price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeatureDTO)) {
            return false;
        }

        FeatureDTO gymDTO = (FeatureDTO) o;
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
            "FeatureDTO{" +
            "id=" +
            id +
            ", name='" +
            name +
            '\'' +
            ", description='" +
            description +
            '\'' +
            ", enabled=" +
            enabled +
            ", price=" +
            price +
            '}'
        );
    }
}
