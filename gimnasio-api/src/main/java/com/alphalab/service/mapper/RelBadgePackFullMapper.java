package com.alphalab.service.mapper;

import com.alphalab.domain.RelBadgePack;
import com.alphalab.service.dto.RelBadgePackDTO;
import com.alphalab.service.dto.RelBadgePackFullDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link RelBadgePack} and its DTO {@link RelBadgePackFullDTO}.
 */
@Mapper(componentModel = "spring")
public interface RelBadgePackFullMapper extends EntityMapper<RelBadgePackFullDTO, RelBadgePack> {
    @Mapping(source = "badge.id", target = "id")
    @Mapping(source = "id", target = "relBadgePackId")
    @Mapping(source = "status", target = "relBadgePackStatus")
    @Mapping(source = "badge.status", target = "status")
    @Mapping(source = "badge.uid", target = "uid")
    @Mapping(source = "badge.gymId", target = "gymId")
    @Mapping(source = "pack.name", target = "packName")
    @Mapping(source = "pack.workoutSessions", target = "packWorkoutSessions")
    @Mapping(source = "badge.lastModifiedDate", target = "lastModifiedDate")
    @Mapping(source = "badge.createdDate", target = "createdDate")
    @Mapping(source = "badge.createdBy", target = "createdBy")
    @Mapping(source = "badge.lastModifiedBy", target = "lastModifiedBy")
    @Mapping(source = "extBadgesDesignation.status", target = "extBadgesDesignationStatus")
    @Mapping(source = "extBadgesDesignation.userId", target = "extBadgesDesignationUserId")
    RelBadgePackFullDTO toDto(RelBadgePack relBadgePackFull);
}
