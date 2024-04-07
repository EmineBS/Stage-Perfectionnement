package com.alphalab.domain;

import com.alphalab.domain.enumeration.BadgePackStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Badge.
 */
@Table("rel_user_gym")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RelUserGym extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Transient
    @JsonIgnoreProperties(value = { "users" }, allowSetters = true)
    private User user;

    @Transient
    @JsonIgnoreProperties(value = { "gyms" }, allowSetters = true)
    private Gym gym;

    @Column("user_id")
    private String userId;

    @Column("gym_id")
    private Long gymId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RelUserGym id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        this.userId = user != null ? user.getId() : null;
    }

    public RelUserGym user(User user) {
        this.setUser(user);
        return this;
    }

    public Gym getGym() {
        return gym;
    }

    public void setGym(Gym gym) {
        this.gym = gym;
        this.gymId = gym != null ? gym.getId() : null;
    }

    public RelUserGym gym(Gym gym) {
        this.setGym(gym);
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelUserGym)) {
            return false;
        }
        return id != null && id.equals(((RelUserGym) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "RelUserGym{" + "id=" + id + ", user=" + user + ", gym=" + gym + ", userId='" + userId + '\'' + ", gymId=" + gymId + '}';
    }
}
