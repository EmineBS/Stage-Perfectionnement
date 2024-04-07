package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;
import com.alphalab.domain.Badge;
import com.alphalab.domain.OffrePack;
import com.alphalab.domain.enumeration.BadgePackStatus;
import com.alphalab.domain.enumeration.BadgeStatus;
import com.alphalab.domain.enumeration.ExtBadgeDesignationStatus;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.alphalab.domain.Gym} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RelBadgePackFullDTO implements Serializable {

    private Long id;

    private Long relBadgePackId;

    private BadgePackStatus relBadgePackStatus;
    private String uid;
    private String status;

    private Long gymId;

    private String packName;
    private Long packWorkoutSessions;

    //    private ExtBadgeDesignationDTO extBadgesDesignation;
    //
    private String extBadgesDesignationUserId;

    private ExtBadgeDesignationStatus extBadgesDesignationStatus;

    private Instant lastModifiedDate;
    private Instant createdDate;
    private String createdBy;
    private String lastModifiedBy;

    private Long remainingSessions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRelBadgePackId() {
        return relBadgePackId;
    }

    public void setRelBadgePackId(Long relBadgePackId) {
        this.relBadgePackId = relBadgePackId;
    }

    public BadgePackStatus getRelBadgePackStatus() {
        return relBadgePackStatus;
    }

    public void setRelBadgePackStatus(BadgePackStatus relBadgePackStatus) {
        this.relBadgePackStatus = relBadgePackStatus;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public Long getPackWorkoutSessions() {
        return packWorkoutSessions;
    }

    public void setPackWorkoutSessions(Long packWorkoutSessions) {
        this.packWorkoutSessions = packWorkoutSessions;
    }

    @Override
    public String toString() {
        return (
            "RelBadgePackFullDTO{" +
            "id=" +
            id +
            ", relBadgePackId=" +
            relBadgePackId +
            ", relBadgePackStatus=" +
            relBadgePackStatus +
            ", uid='" +
            uid +
            '\'' +
            ", status='" +
            status +
            '\'' +
            ", gymId=" +
            gymId +
            ", packName='" +
            packName +
            '\'' +
            ", packWorkoutSessions=" +
            packWorkoutSessions +
            ", extBadgesDesignationUserId='" +
            extBadgesDesignationUserId +
            '\'' +
            ", extBadgesDesignationStatus=" +
            extBadgesDesignationStatus +
            ", lastModifiedDate=" +
            lastModifiedDate +
            ", createdDate=" +
            createdDate +
            ", createdBy='" +
            createdBy +
            '\'' +
            ", lastModifiedBy='" +
            lastModifiedBy +
            '\'' +
            ", remainingSessions=" +
            remainingSessions +
            '}'
        );
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getExtBadgesDesignationUserId() {
        return extBadgesDesignationUserId;
    }

    public void setExtBadgesDesignationUserId(String extBadgesDesignationUserId) {
        this.extBadgesDesignationUserId = extBadgesDesignationUserId;
    }

    public ExtBadgeDesignationStatus getExtBadgesDesignationStatus() {
        return extBadgesDesignationStatus;
    }

    public void setExtBadgesDesignationStatus(ExtBadgeDesignationStatus extBadgesDesignationStatus) {
        this.extBadgesDesignationStatus = extBadgesDesignationStatus;
    }

    public Long getRemainingSessions() {
        return remainingSessions;
    }

    public void setRemainingSessions(Long remainingSessions) {
        this.remainingSessions = remainingSessions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelBadgePackFullDTO)) {
            return false;
        }

        RelBadgePackFullDTO gymDTO = (RelBadgePackFullDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, gymDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
    // prettier-ignore

}
