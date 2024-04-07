package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;
import com.alphalab.domain.Gym;
import com.alphalab.domain.enumeration.BadgeStatus;
import com.alphalab.domain.enumeration.CriteriaDisplay;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.alphalab.domain.Criteria} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CriteriaDTO extends AbstractAuditingEntity<Long> implements Serializable {

    private Long id;
    private String name;
    private String description;

    private Boolean enabled;

    private CriteriaDisplay display;

    private Long gymId;

    private String gymName;

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    @Override
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

    public CriteriaDisplay getDisplay() {
        return display;
    }

    public void setDisplay(CriteriaDisplay display) {
        this.display = display;
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
        if (!(o instanceof CriteriaDTO)) {
            return false;
        }

        CriteriaDTO gymDTO = (CriteriaDTO) o;
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
            "CriteriaDTO{" +
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
            ", display=" +
            display +
            ", gymId=" +
            gymId +
            ", gymName='" +
            gymName +
            '\'' +
            '}'
        );
    }
}
