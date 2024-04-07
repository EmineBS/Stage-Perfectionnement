package com.alphalab.service.mapper;

import com.alphalab.domain.RelBadgePack;
import com.alphalab.service.dto.RelBadgePackDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link RelBadgePack} and its DTO {@link RelBadgePackDTO}.
 */
@Mapper(componentModel = "spring")
public interface RelBadgePackMapper extends EntityMapper<RelBadgePackDTO, RelBadgePack> {}
