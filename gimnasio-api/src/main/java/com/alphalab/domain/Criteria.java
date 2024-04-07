package com.alphalab.domain;

import com.alphalab.domain.enumeration.BadgeStatus;
import com.alphalab.domain.enumeration.CriteriaDisplay;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Criteria.
 */
@Table("criteria")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Criteria extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("enabled")
    private Boolean enabled;

    @Column("display")
    private CriteriaDisplay display;

    @Column("gym_id")
    private Long gymId;

    @Transient
    @JsonIgnoreProperties(value = { "criterias" }, allowSetters = true)
    private Gym gym;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Criteria id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Criteria name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Criteria description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CriteriaDisplay getDisplay() {
        return display;
    }

    public Criteria display(CriteriaDisplay display) {
        this.setDisplay(display);
        return this;
    }

    public void setDisplay(CriteriaDisplay display) {
        this.display = display;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Criteria enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
        this.gymId = gym != null ? gym.getId() : null;
    }

    public Criteria gym(Gym gym) {
        this.gym(gym);
        return this;
    }

    public Gym getGym() {
        return gym;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Criteria)) {
            return false;
        }
        return id != null && id.equals(((Criteria) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "Criteria{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", enabled=" + enabled +
            ", display=" + display +
            ", gymId=" + gymId +
            ", gym=" + gym +
            '}';
    }
}
