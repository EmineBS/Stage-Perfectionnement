package com.alphalab.service.mapper;

import com.alphalab.domain.RelBadgePack;
import com.alphalab.service.dto.RelBadgePackRSDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link RelBadgePack} and its DTO {@link RelBadgePackRSDTO}.
 */
@Mapper(componentModel = "spring")
public interface RelBadgePackRSMapper extends EntityMapper<RelBadgePackRSDTO, RelBadgePack> {}
