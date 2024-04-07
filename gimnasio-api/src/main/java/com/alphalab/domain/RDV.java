package com.alphalab.domain;

import com.alphalab.domain.enumeration.RDVStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Gym.
 */
@Table("rdv")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RDV extends AbstractAuditingEntity<Long> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @Column("from_date")
    private Instant fromDate;

    @Column("to_date")
    private Instant toDate;

    @Column("enabled")
    private Boolean enabled;

    @Column("status")
    private RDVStatus status;

    @Column("calendar_id")
    private Long calendarId;

    @Column("rel_badge_pack_id")
    private Long relBadgePackId;

    @Transient
    @JsonIgnoreProperties(value = { "rdvs" }, allowSetters = true)
    private Calendar calendar;

    @Transient
    @JsonIgnoreProperties(value = { "rdvs" }, allowSetters = true)
    private RelBadgePack relBadgePack;

    @Transient
    @JsonIgnoreProperties(value = { "rdvs" }, allowSetters = true)
    private Profile profile;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public RDV id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public RDV fromDate(Instant fromDate) {
        this.setFromDate(fromDate);
        return this;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public RDV toDate(Instant toDate) {
        this.setToDate(toDate);
        return this;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public RDV enabled(Boolean enabled) {
        this.setEnabled(enabled);
        return this;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public RDVStatus getStatus() {
        return status;
    }

    public RDV status(RDVStatus status) {
        this.setStatus(status);
        return this;
    }

    public void setStatus(RDVStatus status) {
        this.status = status;
    }

    public Long getCalendarId() {
        return calendarId;
    }

    public void setCalendarId(Long calendarId) {
        this.calendarId = calendarId;
    }

    public Long getRelBadgePackId() {
        return relBadgePackId;
    }

    public void setRelBadgePackId(Long badgeId) {
        this.relBadgePackId = badgeId;
    }

    public void setCalendar(Calendar calendar) {
        this.calendar = calendar;
        this.calendarId = calendar != null ? calendar.getId() : null;
    }

    public RDV calendar(Calendar calendar) {
        this.calendar(calendar);
        return this;
    }

    public Calendar getCalendar() {
        return calendar;
    }

    public void setRelBadgePack(RelBadgePack relBadgePack) {
        this.relBadgePack = relBadgePack;
        this.relBadgePackId = relBadgePack != null ? relBadgePack.getId() : null;
    }

    public RDV relBadgePack(RelBadgePack relBadgePack) {
        this.relBadgePack(relBadgePack);
        return this;
    }

    public RelBadgePack getRelBadgePack() {
        return relBadgePack;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RDV)) {
            return false;
        }
        return id != null && id.equals(((RDV) o).id);
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return (
            "RDV{" +
            "id=" +
            id +
            ", fromDate=" +
            fromDate +
            ", toDate=" +
            toDate +
            ", enabled=" +
            enabled +
            ", status='" +
            status +
            '\'' +
            ", calendarId=" +
            calendarId +
            ", relBadgePackId=" +
            relBadgePackId +
            ", calendar=" +
            calendar +
            ", relBadgePack=" +
            relBadgePack +
            '}'
        );
    }
}
