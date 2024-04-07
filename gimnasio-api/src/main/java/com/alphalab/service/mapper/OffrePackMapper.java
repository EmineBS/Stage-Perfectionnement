package com.alphalab.service.mapper;

import com.alphalab.domain.OffrePack;
import com.alphalab.service.dto.OffrePackDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link OffrePack} and its DTO {@link OffrePackDTO}.
 */
@Mapper(componentModel = "spring")
public interface OffrePackMapper extends EntityMapper<OffrePackDTO, OffrePack> {
    @Mapping(source = "gym.id", target = "gymId")
    OffrePack toEntity(OffrePackDTO offrePackDTO);
}
