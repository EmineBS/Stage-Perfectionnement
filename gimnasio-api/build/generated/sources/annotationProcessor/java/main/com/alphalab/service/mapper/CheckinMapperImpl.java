package com.alphalab.service.mapper;

import com.alphalab.domain.Badge;
import com.alphalab.domain.CheckIn;
import com.alphalab.domain.OffrePack;
import com.alphalab.domain.RelBadgePack;
import com.alphalab.service.dto.CheckinDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-27T06:58:23+0100",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class CheckinMapperImpl implements CheckinMapper {

    @Override
    public CheckIn toEntity(CheckinDTO dto) {
        if ( dto == null ) {
            return null;
        }

        CheckIn checkIn = new CheckIn();

        checkIn.setCreatedBy( dto.getCreatedBy() );
        checkIn.setCreatedDate( dto.getCreatedDate() );
        checkIn.setLastModifiedBy( dto.getLastModifiedBy() );
        checkIn.setLastModifiedDate( dto.getLastModifiedDate() );
        checkIn.setId( dto.getId() );
        checkIn.setStatus( dto.getStatus() );
        checkIn.setUserId( dto.getUserId() );
        checkIn.setRelBadgePackId( dto.getRelBadgePackId() );

        return checkIn;
    }

    @Override
    public List<CheckIn> toEntity(List<CheckinDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<CheckIn> list = new ArrayList<CheckIn>( dtoList.size() );
        for ( CheckinDTO checkinDTO : dtoList ) {
            list.add( toEntity( checkinDTO ) );
        }

        return list;
    }

    @Override
    public List<CheckinDTO> toDto(List<CheckIn> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CheckinDTO> list = new ArrayList<CheckinDTO>( entityList.size() );
        for ( CheckIn checkIn : entityList ) {
            list.add( toDto( checkIn ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(CheckIn entity, CheckinDTO dto) {
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
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( dto.getUserId() != null ) {
            entity.setUserId( dto.getUserId() );
        }
        if ( dto.getRelBadgePackId() != null ) {
            entity.setRelBadgePackId( dto.getRelBadgePackId() );
        }
    }

    @Override
    public CheckinDTO toDto(CheckIn checkin) {
        if ( checkin == null ) {
            return null;
        }

        CheckinDTO checkinDTO = new CheckinDTO();

        checkinDTO.setBadgeId( checkinRelBadgePackBadgeId( checkin ) );
        checkinDTO.setBadgeUid( checkinRelBadgePackBadgeUid( checkin ) );
        checkinDTO.setPackId( checkinRelBadgePackPackId( checkin ) );
        checkinDTO.setPackName( checkinRelBadgePackPackName( checkin ) );
        Integer workoutSessions = checkinRelBadgePackPackWorkoutSessions( checkin );
        if ( workoutSessions != null ) {
            checkinDTO.setPackWorkoutSessions( workoutSessions.longValue() );
        }
        checkinDTO.setCreatedBy( checkin.getCreatedBy() );
        checkinDTO.setCreatedDate( checkin.getCreatedDate() );
        checkinDTO.setLastModifiedBy( checkin.getLastModifiedBy() );
        checkinDTO.setLastModifiedDate( checkin.getLastModifiedDate() );
        checkinDTO.setId( checkin.getId() );
        checkinDTO.setUserId( checkin.getUserId() );
        checkinDTO.setStatus( checkin.getStatus() );
        checkinDTO.setRelBadgePackId( checkin.getRelBadgePackId() );

        return checkinDTO;
    }

    private Long checkinRelBadgePackBadgeId(CheckIn checkIn) {
        if ( checkIn == null ) {
            return null;
        }
        RelBadgePack relBadgePack = checkIn.getRelBadgePack();
        if ( relBadgePack == null ) {
            return null;
        }
        Badge badge = relBadgePack.getBadge();
        if ( badge == null ) {
            return null;
        }
        Long id = badge.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String checkinRelBadgePackBadgeUid(CheckIn checkIn) {
        if ( checkIn == null ) {
            return null;
        }
        RelBadgePack relBadgePack = checkIn.getRelBadgePack();
        if ( relBadgePack == null ) {
            return null;
        }
        Badge badge = relBadgePack.getBadge();
        if ( badge == null ) {
            return null;
        }
        String uid = badge.getUid();
        if ( uid == null ) {
            return null;
        }
        return uid;
    }

    private Long checkinRelBadgePackPackId(CheckIn checkIn) {
        if ( checkIn == null ) {
            return null;
        }
        RelBadgePack relBadgePack = checkIn.getRelBadgePack();
        if ( relBadgePack == null ) {
            return null;
        }
        OffrePack pack = relBadgePack.getPack();
        if ( pack == null ) {
            return null;
        }
        Long id = pack.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private String checkinRelBadgePackPackName(CheckIn checkIn) {
        if ( checkIn == null ) {
            return null;
        }
        RelBadgePack relBadgePack = checkIn.getRelBadgePack();
        if ( relBadgePack == null ) {
            return null;
        }
        OffrePack pack = relBadgePack.getPack();
        if ( pack == null ) {
            return null;
        }
        String name = pack.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Integer checkinRelBadgePackPackWorkoutSessions(CheckIn checkIn) {
        if ( checkIn == null ) {
            return null;
        }
        RelBadgePack relBadgePack = checkIn.getRelBadgePack();
        if ( relBadgePack == null ) {
            return null;
        }
        OffrePack pack = relBadgePack.getPack();
        if ( pack == null ) {
            return null;
        }
        Integer workoutSessions = pack.getWorkoutSessions();
        if ( workoutSessions == null ) {
            return null;
        }
        return workoutSessions;
    }
}
