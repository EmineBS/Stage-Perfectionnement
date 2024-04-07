package com.alphalab.service.mapper;

import com.alphalab.domain.Amine;
import com.alphalab.service.dto.AmineDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Amine} and its DTO {@link AmineDTO}.
 */
@Mapper(componentModel = "spring")
public interface AmineMapper extends EntityMapper<AmineDTO, Amine> {
}
