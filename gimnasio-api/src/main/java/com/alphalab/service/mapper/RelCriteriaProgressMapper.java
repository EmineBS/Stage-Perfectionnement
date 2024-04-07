package com.alphalab.service.mapper;

import com.alphalab.domain.RelCriteriaProgress;
import com.alphalab.service.dto.RelCriteriaProgressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link RelCriteriaProgress} and its DTO {@link RelCriteriaProgressDTO}.
 */
@Mapper(componentModel = "spring")
public interface RelCriteriaProgressMapper extends EntityMapper<RelCriteriaProgressDTO, RelCriteriaProgress> {}
