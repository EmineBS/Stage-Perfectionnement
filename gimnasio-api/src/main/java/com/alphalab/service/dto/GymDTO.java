package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;
import com.alphalab.domain.Feature;
import com.alphalab.domain.enumeration.GymStatus;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link com.alphalab.domain.Gym} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GymDTO extends AbstractAuditingEntity<Long> implements Serializable {

    private Long id;

    private String name;
    private String description;

    private Integer badgeAmount;
    private String location;
    private String phoneNumber;
    private String email;
    private String registrationNumber;
    private GymStatus status = GymStatus.ACTIVE;
    private Set<Feature> features = new HashSet<>();

    private Boolean badgeDesignation;
    private Boolean calendar;

    private Boolean progressMonitoring;

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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Integer getBadgeAmount() {
        return badgeAmount;
    }

    public void setBadgeAmount(Integer badgeAmount) {
        this.badgeAmount = badgeAmount;
    }

    public GymStatus getStatus() {
        return status;
    }

    public void setStatus(GymStatus status) {
        this.status = status;
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Feature> features) {
        this.features = features;
    }

    public Boolean getBadgeDesignation() {
        return badgeDesignation;
    }

    public void setBadgeDesignation(Boolean badgeDesignation) {
        this.badgeDesignation = badgeDesignation;
    }

    public Boolean getCalendar() {
        return calendar;
    }

    public void setCalendar(Boolean calendar) {
        this.calendar = calendar;
    }

    public Boolean getProgressMonitoring() {
        return progressMonitoring;
    }

    public void setProgressMonitoring(Boolean progressMonitoring) {
        this.progressMonitoring = progressMonitoring;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GymDTO)) {
            return false;
        }

        GymDTO gymDTO = (GymDTO) o;
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
            "GymDTO{" +
            "id=" +
            id +
            ", name='" +
            name +
            '\'' +
            ", description='" +
            description +
            '\'' +
            ", badgeAmount=" +
            badgeAmount +
            ", location='" +
            location +
            '\'' +
            ", phoneNumber='" +
            phoneNumber +
            '\'' +
            ", email='" +
            email +
            '\'' +
            ", registrationNumber='" +
            registrationNumber +
            '\'' +
            ", status=" +
            status +
            ", features=" +
            features +
            ", badgeDesignation=" +
            badgeDesignation +
            ", calendar=" +
            calendar +
            ", progressMonitoring=" +
            progressMonitoring +
            '}'
        );
    }
}
