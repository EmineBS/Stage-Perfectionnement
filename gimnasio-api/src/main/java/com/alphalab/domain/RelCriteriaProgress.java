package com.alphalab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A RelCriteriaProgress.
 */
@Table("rel_criteria_progress")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RelCriteriaProgress extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("value")
    private Double value;

    @Column("criteria_id")
    private Long criteriaId;

    @Column("progress_id")
    private Long progressId;

    @Transient
    @JsonIgnoreProperties(value = { "criterias" }, allowSetters = true)
    private Criteria criteria;

    @Transient
    @JsonIgnoreProperties(value = { "progresss" }, allowSetters = true)
    private Progress progress;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RelCriteriaProgress id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getValue() {
        return value;
    }

    public RelCriteriaProgress value(Double value) {
        this.setValue(value);
        return this;
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

    public Criteria getCriteria() {
        return criteria;
    }

    public RelCriteriaProgress criteria(Criteria criteria) {
        this.setCriteria(criteria);
        return this;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
        this.criteriaId = criteria != null ? criteria.getId() : null;
    }

    public Progress getProgress() {
        return progress;
    }

    public RelCriteriaProgress progress(Progress progress) {
        this.setProgress(progress);
        return this;
    }

    public void setProgress(Progress progress) {
        this.progress = progress;
        this.progressId = progress != null ? progress.getId() : null;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelCriteriaProgress)) {
            return false;
        }
        return id != null && id.equals(((RelCriteriaProgress) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "RelCriteriaProgress{" +
            "id=" +
            id +
            ", value=" +
            value +
            ", criteriaId=" +
            criteriaId +
            ", progressId=" +
            progressId +
            ", criteria=" +
            criteria +
            ", progress=" +
            progress +
            '}'
        );
    }
}
