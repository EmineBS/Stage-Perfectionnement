package com.alphalab.domain;

import com.alphalab.domain.enumeration.GymStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Gym.
 */
@Table("gym")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Gym extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("name")
    private String name;

    @Column("description")
    private String description;

    @Column("badge_amount")
    private Integer badgeAmount;

    @Column("location")
    private String location;

    @Column("phone_number")
    private String phoneNumber;

    @Column("email")
    private String email;

    @Column("registration_number")
    private String registrationNumber;

    @Column("status")
    private GymStatus status;

    @Transient
    @JsonIgnoreProperties(value = { "gyms" }, allowSetters = true)
    private Set<Feature> features = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Gym id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public Gym name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public Gym description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getBadgeAmount() {
        return badgeAmount;
    }

    public Gym badgeAmount(Integer badgeAmount) {
        this.badgeAmount(badgeAmount);
        return this;
    }

    public void setBadgeAmount(Integer badgeAmount) {
        this.badgeAmount = badgeAmount;
    }

    public String getLocation() {
        return location;
    }

    public Gym location(String location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Gym phoneNumber(String phoneNumber) {
        this.setPhoneNumber(phoneNumber);
        return this;
    }

    public void setPhoneNumber(String phone_number) {
        this.phoneNumber = phone_number;
    }

    public String getEmail() {
        return email;
    }

    public Gym email(String email) {
        this.setEmail(email);
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public Gym registrationNumber(String registrationNumber) {
        this.setRegistrationNumber(registrationNumber);
        return this;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public GymStatus getStatus() {
        return status;
    }

    public Gym status(GymStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(GymStatus status) {
        this.status = status;
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    public Gym features(Set<Feature> features) {
        this.features(features);
        return this;
    }

    public void setFeatures(Set<Feature> features) {
        this.features = features;
    }

    public Gym addFeature(Feature feature) {
        this.features.add(feature);
        return this;
    }

    public Gym removeFeature(Feature feature) {
        this.features.remove(feature);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Gym)) {
            return false;
        }
        return id != null && id.equals(((Gym) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "Gym{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", badgeAmount=" + badgeAmount +
            ", location='" + location + '\'' +
            ", phoneNumber='" + phoneNumber + '\'' +
            ", email='" + email + '\'' +
            ", registrationNumber='" + registrationNumber + '\'' +
            ", status=" + status +
            ", features=" + features +
            '}';
    }
}
