package com.alphalab.service.dto;

import com.alphalab.domain.*;
import com.alphalab.domain.enumeration.BadgePackStatus;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.alphalab.domain.RelUserGym} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class RelUserGymDTO extends AbstractAuditingEntity<Long> implements Serializable {

    private Long id;

    private Long gymId;

    private String userId;

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RelUserGymDTO)) {
            return false;
        }

        RelUserGymDTO gymDTO = (RelUserGymDTO) o;
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


    @Override
    public String toString() {
        return "RelUserGymDTO{" +
            "id=" + id +
            ", gymId=" + gymId +
            ", userId='" + userId + '\'' +
            '}';
    }
}
