package com.alphalab.service.mapper;

import com.alphalab.domain.Badge;
import com.alphalab.domain.Gym;
import com.alphalab.service.dto.BadgeDTO;
import com.alphalab.service.dto.GymDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Gym} and its DTO {@link GymDTO}.
 */
@Mapper(componentModel = "spring")
public interface GymMapper extends EntityMapper<GymDTO, Gym> {
    /*
    @Mapping(source = "user.id", target = "userId")
    Gym toEntity(GymDTO gymDTO);
*/

}
