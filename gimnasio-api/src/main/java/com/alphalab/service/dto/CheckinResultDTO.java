package com.alphalab.service.dto;

import com.alphalab.domain.AbstractAuditingEntity;
import com.alphalab.domain.enumeration.CheckinStatus;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.alphalab.domain.Gym} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class CheckinResultDTO extends CheckinDTO implements Serializable {

    private Long checkinIndex;

    private Long checkinLeft;

    public Long getCheckinIndex() {
        return checkinIndex;
    }

    public Long getCheckinLeft() {
        return checkinLeft;
    }

    public void setCheckinIndex(Long checkinIndex) {
        this.checkinIndex = checkinIndex;
    }

    public void setCheckinLeft(Long checkinLeft) {
        this.checkinLeft = checkinLeft;
    }

    public CheckinResultDTO() {}

    public CheckinResultDTO(CheckinDTO checkinDTO, Long checkinIndex, Long checkinLeft) {
        this.setId(checkinDTO.getId());
        this.setUserId(checkinDTO.getUserId());
        this.setStatus(checkinDTO.getStatus());
        this.setBadgeId(checkinDTO.getBadgeId());
        this.setBadgeUid(checkinDTO.getBadgeUid());
        this.setPackId(checkinDTO.getPackId());
        this.setPackName(checkinDTO.getPackName());
        this.setPackWorkoutSessions(checkinDTO.getPackWorkoutSessions());
        this.setRelBadgePackId(checkinDTO.getRelBadgePackId());
        this.setCheckinIndex(checkinIndex);
        this.setCheckinLeft(checkinLeft);
    }

    @Override
    public String toString() {
        return (
            "CheckinDisplayDTO [id=" +
            getId() +
            ", userId=" +
            getUserId() +
            ", status=" +
            getStatus() +
            ", badgeId=" +
            getBadgeId() +
            ", badgeUid=" +
            getBadgeUid() +
            ", packId=" +
            getPackId() +
            ", packName=" +
            getPackName() +
            ", packWorkoutSessions=" +
            getPackWorkoutSessions() +
            ", relBadgePackId=" +
            getPackId() +
            ", checkinIndex=" +
            getCheckinIndex() +
            ", checkinLeft=" +
            getCheckinLeft() +
            "]"
        );
    }
}
