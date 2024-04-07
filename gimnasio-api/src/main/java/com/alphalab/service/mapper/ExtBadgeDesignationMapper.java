package com.alphalab.service.mapper;

import com.alphalab.domain.CheckIn;
import com.alphalab.domain.ExtBadgeDesignation;
import com.alphalab.service.dto.CheckinDTO;
import com.alphalab.service.dto.ExtBadgeDesignationDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link ExtBadgeDesignation} and its DTO {@link ExtBadgeDesignationDTO}.
 */
@Mapper(componentModel = "spring")
public interface ExtBadgeDesignationMapper extends EntityMapper<ExtBadgeDesignationDTO, ExtBadgeDesignation> {}
