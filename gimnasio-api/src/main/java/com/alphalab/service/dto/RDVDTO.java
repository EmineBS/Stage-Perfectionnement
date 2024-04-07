package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;
import com.alphalab.domain.Calendar;
import com.alphalab.domain.enumeration.BadgePackStatus;
import com.alphalab.domain.enumeration.RDVStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.alphalab.domain.Gym} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RDVDTO extends AbstractAuditingEntity<Long> implements Serializable {

    private Long id;
    private Instant fromDate;
    private Instant toDate;
    private Boolean enabled;

    private RDVStatus status = RDVStatus.CONFIRMED;

    private Long calendarId;

    private Long relBadgePackId;

    private Long badgeId;

    private BadgePackStatus badgePackStatus;

    private String packName;
    private String packId;
    private String badgeUid;
    private String profileEmail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getFromDate() {
        return fromDate;
    }

    public void setFromDate(Instant fromDate) {
        this.fromDate = fromDate;
    }

    public Instant getToDate() {
        return toDate;
    }

    public void setToDate(Instant toDate) {
        this.toDate = toDate;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public RDVStatus getStatus() {
        return status;
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

    public void setRelBadgePackId(Long relBadgePackId) {
        this.relBadgePackId = relBadgePackId;
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

    public BadgePackStatus getBadgePackStatus() {
        return badgePackStatus;
    }

    public void setBadgePackStatus(BadgePackStatus badgePackStatus) {
        this.badgePackStatus = badgePackStatus;
    }

    public String getProfileEmail() {
        return profileEmail;
    }

    public void setProfileEmail(String profileEmail) {
        this.profileEmail = profileEmail;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getPackId() {
        return packId;
    }

    public void setPackId(String packId) {
        this.packId = packId;
    }

    public String getBadgeUid() {
        return badgeUid;
    }

    public void setBadgeUid(String badgeUid) {
        this.badgeUid = badgeUid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RDVDTO)) {
            return false;
        }

        RDVDTO gymDTO = (RDVDTO) o;
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
            "RDVDTO{" +
            "id=" +
            id +
            ", fromDate=" +
            fromDate +
            ", toDate=" +
            toDate +
            ", enabled=" +
            enabled +
            ", status=" +
            status +
            ", calendarId=" +
            calendarId +
            ", relBadgePackId=" +
            relBadgePackId +
            ", badgeId=" +
            badgeId +
            ", badgePackStatus=" +
            badgePackStatus +
            ", packName='" +
            packName +
            '\'' +
            ", packId='" +
            packId +
            '\'' +
            ", badgeUid='" +
            badgeUid +
            '\'' +
            '}'
        );
    }
}
