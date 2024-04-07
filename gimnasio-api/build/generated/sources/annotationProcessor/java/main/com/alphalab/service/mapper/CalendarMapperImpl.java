package com.alphalab.service.mapper;

import com.alphalab.domain.Calendar;
import com.alphalab.domain.Gym;
import com.alphalab.service.dto.CalendarDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-27T06:58:24+0100",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class CalendarMapperImpl implements CalendarMapper {

    @Override
    public Calendar toEntity(CalendarDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Calendar calendar = new Calendar();

        calendar.setCreatedBy( dto.getCreatedBy() );
        calendar.setCreatedDate( dto.getCreatedDate() );
        calendar.setLastModifiedBy( dto.getLastModifiedBy() );
        calendar.setLastModifiedDate( dto.getLastModifiedDate() );
        calendar.setId( dto.getId() );
        calendar.setEnabled( dto.getEnabled() );
        calendar.setStartHour( dto.getStartHour() );
        calendar.setEndHour( dto.getEndHour() );
        calendar.setUnit( dto.getUnit() );
        List<Integer> list = dto.getOffDays();
        if ( list != null ) {
            calendar.setOffDays( new ArrayList<Integer>( list ) );
        }
        calendar.setGymId( dto.getGymId() );

        return calendar;
    }

    @Override
    public List<Calendar> toEntity(List<CalendarDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Calendar> list = new ArrayList<Calendar>( dtoList.size() );
        for ( CalendarDTO calendarDTO : dtoList ) {
            list.add( toEntity( calendarDTO ) );
        }

        return list;
    }

    @Override
    public List<CalendarDTO> toDto(List<Calendar> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CalendarDTO> list = new ArrayList<CalendarDTO>( entityList.size() );
        for ( Calendar calendar : entityList ) {
            list.add( toDto( calendar ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Calendar entity, CalendarDTO dto) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getCreatedBy() != null ) {
            entity.setCreatedBy( dto.getCreatedBy() );
        }
        if ( dto.getCreatedDate() != null ) {
            entity.setCreatedDate( dto.getCreatedDate() );
        }
        if ( dto.getLastModifiedBy() != null ) {
            entity.setLastModifiedBy( dto.getLastModifiedBy() );
        }
        if ( dto.getLastModifiedDate() != null ) {
            entity.setLastModifiedDate( dto.getLastModifiedDate() );
        }
        if ( dto.getId() != null ) {
            entity.setId( dto.getId() );
        }
        if ( dto.getEnabled() != null ) {
            entity.setEnabled( dto.getEnabled() );
        }
        if ( dto.getStartHour() != null ) {
            entity.setStartHour( dto.getStartHour() );
        }
        if ( dto.getEndHour() != null ) {
            entity.setEndHour( dto.getEndHour() );
        }
        if ( dto.getUnit() != null ) {
            entity.setUnit( dto.getUnit() );
        }
        if ( entity.getOffDays() != null ) {
            List<Integer> list = dto.getOffDays();
            if ( list != null ) {
                entity.getOffDays().clear();
                entity.getOffDays().addAll( list );
            }
        }
        else {
            List<Integer> list = dto.getOffDays();
            if ( list != null ) {
                entity.setOffDays( new ArrayList<Integer>( list ) );
            }
        }
        if ( dto.getGymId() != null ) {
            entity.setGymId( dto.getGymId() );
        }
    }

    @Override
    public CalendarDTO toDto(Calendar calendar) {
        if ( calendar == null ) {
            return null;
        }

        CalendarDTO calendarDTO = new CalendarDTO();

        calendarDTO.setGymName( calendarGymName( calendar ) );
        calendarDTO.setCreatedBy( calendar.getCreatedBy() );
        calendarDTO.setCreatedDate( calendar.getCreatedDate() );
        calendarDTO.setLastModifiedBy( calendar.getLastModifiedBy() );
        calendarDTO.setLastModifiedDate( calendar.getLastModifiedDate() );
        calendarDTO.setId( calendar.getId() );
        calendarDTO.setEnabled( calendar.getEnabled() );
        calendarDTO.setGymId( calendar.getGymId() );
        calendarDTO.setStartHour( calendar.getStartHour() );
        calendarDTO.setEndHour( calendar.getEndHour() );
        calendarDTO.setUnit( calendar.getUnit() );
        List<Integer> list = calendar.getOffDays();
        if ( list != null ) {
            calendarDTO.setOffDays( new ArrayList<Integer>( list ) );
        }

        return calendarDTO;
    }

    private String calendarGymName(Calendar calendar) {
        if ( calendar == null ) {
            return null;
        }
        Gym gym = calendar.getGym();
        if ( gym == null ) {
            return null;
        }
        String name = gym.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
