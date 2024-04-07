package com.alphalab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Pack.
 */
@Table("pack")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OffrePack extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("workout_sessions")
    private Integer workoutSessions;

    @Column("price")
    private Double price;

    @Column("auto_confirm")
    private Boolean autoConfirm;

    @Column("rdv_enabled")
    private Boolean rdvEnabled;

    @Transient
    @JsonIgnoreProperties(value = { "gym" }, allowSetters = true)
    private Gym gym;

    @Column("gym_id")
    private Long gymId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public OffrePack id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public OffrePack name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public OffrePack description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getWorkoutSessions() {
        return workoutSessions;
    }

    public OffrePack workoutSessions(Integer workoutSessions) {
        this.setWorkoutSessions(workoutSessions);
        return this;
    }

    public void setWorkoutSessions(Integer workoutSessions) {
        this.workoutSessions = workoutSessions;
    }

    public Double getPrice() {
        return price;
    }

    public OffrePack price(Double price) {
        this.setPrice(price);
        return this;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAutoConfirm() {
        return autoConfirm;
    }

    public OffrePack autoConfirm(Boolean autoConfirm) {
        this.setAutoConfirm(autoConfirm);
        return this;
    }

    public void setAutoConfirm(Boolean autoConfirm) {
        this.autoConfirm = autoConfirm;
    }

    public Boolean getRdvEnabled() {
        return rdvEnabled;
    }

    public OffrePack rdvEnabled(Boolean rdvEnabled) {
        this.setRdvEnabled(rdvEnabled);
        return this;
    }

    public void setRdvEnabled(Boolean rdvEnabled) {
        this.rdvEnabled = rdvEnabled;
    }

    public Gym getGym() {
        return gym;
    }

    public OffrePack gym(Gym gym) {
        this.setGym(gym);
        return this;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
    }

    public Long getGymId() {
        return gymId;
    }

    public OffrePack gymId(Long gymId) {
        this.setGymId(gymId);
        return this;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OffrePack)) {
            return false;
        }
        return id != null && id.equals(((OffrePack) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "pack{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", workoutSessions=" + workoutSessions +
            ", price=" + price +
            '}';
    }
}
