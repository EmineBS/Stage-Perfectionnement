package com.alphalab.service.mapper;

import com.alphalab.domain.RelUserGym;
import com.alphalab.service.dto.RelUserGymDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link RelUserGym} and its DTO {@link RelUserGymDTO}.
 */
@Mapper(componentModel = "spring")
public interface RelUserGymMapper extends EntityMapper<RelUserGymDTO, RelUserGym> {}
