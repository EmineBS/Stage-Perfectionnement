package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;
import com.alphalab.domain.enumeration.CheckinStatus;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.alphalab.domain.Gym} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CheckinDTO extends AbstractAuditingEntity<Long> implements Serializable {

    private Long id;

    private String userId;

    private CheckinStatus status = CheckinStatus.CONFIRMED;

    private Long badgeId;

    private String badgeUid;

    private Long packId;

    private String packName;

    private Long packWorkoutSessions;

    private Long relBadgePackId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public CheckinStatus getStatus() {
        return status;
    }

    public void setStatus(CheckinStatus status) {
        this.status = status;
    }

    public Long getBadgeId() {
        return badgeId;
    }

    public void setBadgeId(Long badgeId) {
        this.badgeId = badgeId;
    }

    public String getBadgeUid() {
        return badgeUid;
    }

    public void setBadgeUid(String badgeUid) {
        this.badgeUid = badgeUid;
    }

    public Long getPackId() {
        return packId;
    }

    public void setPackId(Long packId) {
        this.packId = packId;
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

    public CheckinDTO() {}

    public Long getRelBadgePackId() {
        return relBadgePackId;
    }

    public void setRelBadgePackId(Long relBadgePackId) {
        this.relBadgePackId = relBadgePackId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CheckinDTO)) {
            return false;
        }

        CheckinDTO checkinDTO = (CheckinDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, checkinDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    @Override
    public String toString() {
        return (
            "CheckinDisplayDTO [id=" +
            id +
            ", userId=" +
            userId +
            ", status=" +
            status +
            ", badgeId=" +
            badgeId +
            ", badgeUid=" +
            badgeUid +
            ", packId=" +
            packId +
            ", packName=" +
            packName +
            ", packWorkoutSessions=" +
            packWorkoutSessions +
            ", relBadgePackId=" +
            relBadgePackId +
            "]"
        );
    }
}
