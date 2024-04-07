package com.alphalab.service.mapper;

import com.alphalab.domain.RDV;
import com.alphalab.domain.RelBadgePack;
import com.alphalab.service.dto.RDVDTO;
import com.alphalab.service.dto.RelBadgePackFullDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link RDV} and its DTO {@link RDVDTO}.
 */
@Mapper(componentModel = "spring")
public interface RDVMapper extends EntityMapper<RDVDTO, RDV> {
    @Mapping(source = "relBadgePack.status", target = "badgePackStatus")
    @Mapping(source = "relBadgePack.badge.id", target = "badgeId")
    @Mapping(source = "relBadgePack.badge.uid", target = "badgeUid")
    @Mapping(source = "relBadgePack.pack.id", target = "packId")
    @Mapping(source = "relBadgePack.pack.name", target = "packName")
    @Mapping(source = "profile.email", target = "profileEmail")
    RDVDTO toDto(RDV rdv);
}
