package com.alphalab.service.mapper;

import com.alphalab.domain.RelGymFeature;
import com.alphalab.service.dto.RelGymFeatureDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link RelGymFeature} and its DTO {@link RelGymFeatureDTO}.
 */
@Mapper(componentModel = "spring")
public interface RelGymFeatureMapper extends EntityMapper<RelGymFeatureDTO, RelGymFeature> {

    @Mapping(source = "feature.name", target = "featureName")
    RelGymFeatureDTO toDto(RelGymFeature relGymFeature);


}
