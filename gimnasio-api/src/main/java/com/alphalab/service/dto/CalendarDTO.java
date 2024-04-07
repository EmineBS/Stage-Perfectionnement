package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;
import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

/**
 * A DTO for the {@link com.alphalab.domain.Gym} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CalendarDTO extends AbstractAuditingEntity<Long> implements Serializable {

    private Long id;

    private LocalTime startHour;

    private LocalTime endHour;

    private Integer unit;

    private List<Integer> offDays;
    private Boolean enabled = false;
    private Long gymId;

    private String gymName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEnabled() {
        return enabled;
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

    public LocalTime getStartHour() {
        return startHour;
    }

    public void setStartHour(LocalTime startHour) {
        this.startHour = startHour;
    }

    public LocalTime getEndHour() {
        return endHour;
    }

    public void setEndHour(LocalTime endHour) {
        this.endHour = endHour;
    }

    public Integer getUnit() {
        return unit;
    }

    public void setUnit(Integer unit) {
        this.unit = unit;
    }

    public List<Integer> getOffDays() {
        return offDays;
    }

    public void setOffDays(List<Integer> offDays) {
        this.offDays = offDays;
    }

    public String getGymName() {
        return gymName;
    }

    public void setGymName(String gymName) {
        this.gymName = gymName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CalendarDTO)) {
            return false;
        }

        CalendarDTO gymDTO = (CalendarDTO) o;
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
        return "CalendarDTO{" +
            "id=" + id +
            ", startHour=" + startHour +
            ", endHour=" + endHour +
            ", unit=" + unit +
            ", offDays=" + offDays +
            ", enabled=" + enabled +
            ", gymId=" + gymId +
            ", gymName='" + gymName + '\'' +
            '}';
    }
}
