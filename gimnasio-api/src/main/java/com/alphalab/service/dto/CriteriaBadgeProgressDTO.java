package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;
import com.alphalab.domain.enumeration.CriteriaDisplay;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * A DTO for the {@link com.alphalab.domain.Criteria} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CriteriaBadgeProgressDTO {

    private Long id;
    private String name;
    private String description;

    private Boolean enabled;

    private ArrayList<ProgressValue> progressValues = new ArrayList<>();

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

    public ArrayList<ProgressValue> getProgressValues() {
        return progressValues;
    }

    public void setProgressValues(ArrayList<ProgressValue> progressValues) {
        this.progressValues = progressValues;
    }

    public void addProgressValuesItem(ProgressValue progressValue) {
        this.progressValues.add(progressValue);
    }

    @Override
    public String toString() {
        return (
            "CriteriaBadgeProgressDTO{" +
            "id='" +
            id +
            '\'' +
            "name='" +
            name +
            '\'' +
            ", description='" +
            description +
            '\'' +
            ", enabled=" +
            enabled +
            ", progressValues=" +
            progressValues +
            '}'
        );
    }
}
