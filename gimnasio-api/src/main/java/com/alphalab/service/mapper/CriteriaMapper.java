package com.alphalab.service.mapper;

import com.alphalab.domain.Criteria;
import com.alphalab.service.dto.CriteriaDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Criteria} and its DTO {@link CriteriaDTO}.
 */
@Mapper(componentModel = "spring")
public interface CriteriaMapper extends EntityMapper<CriteriaDTO, Criteria> {
    @Mapping(source = "gym.name", target = "gymName")
    CriteriaDTO toDto(Criteria criteria);
}
