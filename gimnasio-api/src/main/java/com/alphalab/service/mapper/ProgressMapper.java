package com.alphalab.service.mapper;

import com.alphalab.domain.Progress;
import com.alphalab.service.dto.ProgressDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Progress} and its DTO {@link ProgressDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProgressMapper extends EntityMapper<ProgressDTO, Progress> {}
