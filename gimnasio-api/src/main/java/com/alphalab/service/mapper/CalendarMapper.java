package com.alphalab.service.mapper;

import com.alphalab.domain.Calendar;
import com.alphalab.service.dto.CalendarDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper for the entity {@link Calendar} and its DTO {@link CalendarDTO}.
 */
@Mapper(componentModel = "spring")
public interface CalendarMapper extends EntityMapper<CalendarDTO, Calendar> {

    @Mapping(source = "gym.name", target = "gymName")
    CalendarDTO toDto(Calendar calendar);

}
