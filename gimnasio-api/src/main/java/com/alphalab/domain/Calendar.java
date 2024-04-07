package com.alphalab.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Calendar.
 */
@Table("calendar")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Calendar extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("start_hour")
    private LocalTime startHour;

    @Column("end_hour")
    private LocalTime endHour;

    @Column("unit")
    private Integer unit;

    @Column("off_days")
    private List<Integer> offDays;

    @Column("enabled")
    private Boolean enabled;

    @Column("gym_id")
    private Long gymId;

    @Transient
    @JsonIgnoreProperties(value = { "calendars" }, allowSetters = true)
    private Gym gym;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Calendar id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Calendar enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public LocalTime getStartHour() {
        return startHour;
    }

    public Calendar startHour(LocalTime startHour) {
        this.setStartHour(startHour);
        return this;
    }

    public void setStartHour(LocalTime startHour) {
        this.startHour = startHour;
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    public Calendar endHour(LocalTime endHour) {
        this.setEndHour(endHour);
        return this;
    }

    public void setEndHour(LocalTime endHour) {
        this.endHour = endHour;
    }

    public Integer getUnit() {
        return unit;
    }

    public Calendar unit(Integer unit) {
        this.setUnit(unit);
        return this;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public List<Integer> getOffDays() {
        return offDays;
    }

    public Calendar workingDays(List<Integer> workingDays) {
        this.setOffDays(workingDays);
        return this;
    }

    public void setOffDays(List<Integer> offDays) {
        this.offDays = offDays;
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

    public Calendar gym(Gym gym) {
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
        if (!(o instanceof Calendar)) {
            return false;
        }
        return id != null && id.equals(((Calendar) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore

    @Override
    public String toString() {
        return "Calendar{" +
            "id=" + id +
            ", startHour=" + startHour +
            ", endHour=" + endHour +
            ", unit='" + unit + '\'' +
            ", workingDays=" + offDays +
            ", enabled=" + enabled +
            ", gymId=" + gymId +
            ", gym=" + gym +
            '}';
    }
}
