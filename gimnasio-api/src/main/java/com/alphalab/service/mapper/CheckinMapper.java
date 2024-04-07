package com.alphalab.service.mapper;

import com.alphalab.domain.CheckIn;
import com.alphalab.domain.RelBadgePack;
import com.alphalab.service.dto.CheckinDTO;
import com.alphalab.service.dto.RelBadgePackDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link RelBadgePack} and its DTO {@link RelBadgePackDTO}.
 */
@Mapper(componentModel = "spring")
public interface CheckinMapper extends EntityMapper<CheckinDTO, CheckIn> {
    @Mapping(source = "relBadgePack.badge.id", target = "badgeId")
    @Mapping(source = "relBadgePack.badge.uid", target = "badgeUid")
    @Mapping(source = "relBadgePack.pack.id", target = "packId")
    @Mapping(source = "relBadgePack.pack.name", target = "packName")
    @Mapping(source = "relBadgePack.pack.workoutSessions", target = "packWorkoutSessions")
    CheckinDTO toDto(CheckIn checkin);
}
