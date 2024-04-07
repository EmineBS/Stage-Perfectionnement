package com.alphalab.service.dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A DTO for the {@link com.alphalab.domain.Gym} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SaveProgressDTO extends ProgressDTO implements Serializable {

    private Long id;

    private ArrayList<CriteriaValue> criteriaValues = new ArrayList<>();

    private Long badgeId;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public ArrayList<CriteriaValue> getCriteriaValues() {
        return criteriaValues;
    }

    public void setCriteriaValues(ArrayList<CriteriaValue> criteriaValues) {
        this.criteriaValues = criteriaValues;
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

    @Override
    public String toString() {
        return "SaveProgressDTO{" + "id=" + id + ", criteriaValues=" + criteriaValues + ", badgeId=" + badgeId + '}';
    }
}
