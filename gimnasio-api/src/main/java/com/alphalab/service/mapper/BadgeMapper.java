package com.alphalab.service.mapper;

import com.alphalab.domain.Badge;
import com.alphalab.domain.Gym;
import com.alphalab.service.dto.BadgeDTO;
import com.alphalab.service.dto.GymDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Badge} and its DTO {@link BadgeDTO}.
 */
@Mapper(componentModel = "spring")
public interface BadgeMapper extends EntityMapper<BadgeDTO, Badge> {
    @Mapping(source = "gym.name", target = "gymName")
    BadgeDTO toDto(Badge badge);
}
