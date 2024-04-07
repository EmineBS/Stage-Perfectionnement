package com.alphalab.service.mapper;

import com.alphalab.domain.Feature;
import com.alphalab.service.dto.FeatureDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Feature} and its DTO {@link FeatureDTO}.
 */
@Mapper(componentModel = "spring")
public interface FeatureMapper extends EntityMapper<FeatureDTO, Feature> {
    /*
    @Mapping(source = "user.id", target = "userId")
    Feature toEntity(FeatureDTO gymDTO);
*/

}
