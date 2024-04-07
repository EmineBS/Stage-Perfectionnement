package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.alphalab.domain.Gym} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class OffrePackDTO extends AbstractAuditingEntity<Long> implements Serializable {

    private Long id;

    private String name;
    private String description;

    private Integer workoutSessions;
    private Double price;
    private Boolean autoConfirm;
    private Boolean rdvEnabled;

    private GymDTO gym;

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

    public Integer getWorkoutSessions() {
        return workoutSessions;
    }

    public void setWorkoutSessions(Integer workoutSessions) {
        this.workoutSessions = workoutSessions;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getAutoConfirm() {
        return autoConfirm;
    }

    public void setAutoConfirm(Boolean autoConfirm) {
        this.autoConfirm = autoConfirm;
    }

    public Boolean getRdvEnabled() {
        return rdvEnabled;
    }

    public void setRdvEnabled(Boolean rdvEnabled) {
        this.rdvEnabled = rdvEnabled;
    }

    public GymDTO getGym() {
        return gym;
    }

    public void setGym(GymDTO gym) {
        this.gym = gym;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof OffrePackDTO)) {
            return false;
        }

        OffrePackDTO gymDTO = (OffrePackDTO) o;
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
            "OffrePackDTO{" +
            "id=" +
            id +
            ", name='" +
            name +
            '\'' +
            ", description='" +
            description +
            '\'' +
            ", workoutSessions=" +
            workoutSessions +
            ", price=" +
            price +
            ", auto_confirm=" +
            autoConfirm +
            ", rdv_enabled=" +
            rdvEnabled +
            ", gym=" +
            gym +
            '}'
        );
    }
}
