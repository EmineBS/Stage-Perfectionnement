package com.alphalab.domain;

import com.alphalab.domain.enumeration.BadgeStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.Pack;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Badge.
 */
@Table("badge")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Badge extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("uid")
    private String uid;

    @Column("status")
    private BadgeStatus status;

    @Column("enabled")
    private Boolean enabled;

    @Column("gym_id")
    private Long gymId;

    @Transient
    @JsonIgnoreProperties(value = { "badges" }, allowSetters = true)
    private Gym gym;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Badge id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public Badge uid(String uid) {
        this.setUid(uid);
        return this;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public BadgeStatus getStatus() {
        return status;
    }

    public void setStatus(BadgeStatus status) {
        this.status = status;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Badge enabled(Boolean enabled) {
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

    public Badge gym(Gym gym) {
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
        if (!(o instanceof Badge)) {
            return false;
        }
        return id != null && id.equals(((Badge) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore


    @Override
    public String toString() {
        return "Badge{" +
            "id=" + id +
            ", uid='" + uid + '\'' +
            ", status='" + status + '\'' +
            ", enabled='" + enabled + '\'' +
            ", gymId=" + gymId +
            ", gym=" + gym +
            '}';
    }
}
