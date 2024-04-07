package com.alphalab.service.mapper;

import com.alphalab.domain.Badge;
import com.alphalab.domain.ExtBadgeDesignation;
import com.alphalab.domain.OffrePack;
import com.alphalab.domain.RelBadgePack;
import com.alphalab.domain.enumeration.BadgePackStatus;
import com.alphalab.domain.enumeration.BadgeStatus;
import com.alphalab.domain.enumeration.ExtBadgeDesignationStatus;
import com.alphalab.service.dto.RelBadgePackFullDTO;
import java.time.Instant;
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
public class RelBadgePackFullMapperImpl implements RelBadgePackFullMapper {

    @Override
    public RelBadgePack toEntity(RelBadgePackFullDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RelBadgePack relBadgePack = new RelBadgePack();

        relBadgePack.setCreatedBy( dto.getCreatedBy() );
        relBadgePack.setCreatedDate( dto.getCreatedDate() );
        relBadgePack.setLastModifiedBy( dto.getLastModifiedBy() );
        relBadgePack.setLastModifiedDate( dto.getLastModifiedDate() );
        relBadgePack.setId( dto.getId() );
        if ( dto.getStatus() != null ) {
            relBadgePack.setStatus( Enum.valueOf( BadgePackStatus.class, dto.getStatus() ) );
        }

        return relBadgePack;
    }

    @Override
    public List<RelBadgePack> toEntity(List<RelBadgePackFullDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<RelBadgePack> list = new ArrayList<RelBadgePack>( dtoList.size() );
        for ( RelBadgePackFullDTO relBadgePackFullDTO : dtoList ) {
            list.add( toEntity( relBadgePackFullDTO ) );
        }

        return list;
    }

    @Override
    public List<RelBadgePackFullDTO> toDto(List<RelBadgePack> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RelBadgePackFullDTO> list = new ArrayList<RelBadgePackFullDTO>( entityList.size() );
        for ( RelBadgePack relBadgePack : entityList ) {
            list.add( toDto( relBadgePack ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(RelBadgePack entity, RelBadgePackFullDTO dto) {
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
            entity.setStatus( Enum.valueOf( BadgePackStatus.class, dto.getStatus() ) );
        }
    }

    @Override
    public RelBadgePackFullDTO toDto(RelBadgePack relBadgePackFull) {
        if ( relBadgePackFull == null ) {
            return null;
        }

        RelBadgePackFullDTO relBadgePackFullDTO = new RelBadgePackFullDTO();

        relBadgePackFullDTO.setId( relBadgePackFullBadgeId( relBadgePackFull ) );
        relBadgePackFullDTO.setRelBadgePackId( relBadgePackFull.getId() );
        relBadgePackFullDTO.setRelBadgePackStatus( relBadgePackFull.getStatus() );
        BadgeStatus status = relBadgePackFullBadgeStatus( relBadgePackFull );
        if ( status != null ) {
            relBadgePackFullDTO.setStatus( status.name() );
        }
        relBadgePackFullDTO.setUid( relBadgePackFullBadgeUid( relBadgePackFull ) );
        relBadgePackFullDTO.setGymId( relBadgePackFullBadgeGymId( relBadgePackFull ) );
        relBadgePackFullDTO.setPackName( relBadgePackFullPackName( relBadgePackFull ) );
        Integer workoutSessions = relBadgePackFullPackWorkoutSessions( relBadgePackFull );
        if ( workoutSessions != null ) {
            relBadgePackFullDTO.setPackWorkoutSessions( workoutSessions.longValue() );
        }
        relBadgePackFullDTO.setLastModifiedDate( relBadgePackFullBadgeLastModifiedDate( relBadgePackFull ) );
        relBadgePackFullDTO.setCreatedDate( relBadgePackFullBadgeCreatedDate( relBadgePackFull ) );
        relBadgePackFullDTO.setCreatedBy( relBadgePackFullBadgeCreatedBy( relBadgePackFull ) );
        relBadgePackFullDTO.setLastModifiedBy( relBadgePackFullBadgeLastModifiedBy( relBadgePackFull ) );
        relBadgePackFullDTO.setExtBadgesDesignationStatus( relBadgePackFullExtBadgesDesignationStatus( relBadgePackFull ) );
        relBadgePackFullDTO.setExtBadgesDesignationUserId( relBadgePackFullExtBadgesDesignationUserId( relBadgePackFull ) );

        return relBadgePackFullDTO;
    }

    private Long relBadgePackFullBadgeId(RelBadgePack relBadgePack) {
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

    private BadgeStatus relBadgePackFullBadgeStatus(RelBadgePack relBadgePack) {
        if ( relBadgePack == null ) {
            return null;
        }
        Badge badge = relBadgePack.getBadge();
        if ( badge == null ) {
            return null;
        }
        BadgeStatus status = badge.getStatus();
        if ( status == null ) {
            return null;
        }
        return status;
    }

    private String relBadgePackFullBadgeUid(RelBadgePack relBadgePack) {
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

    private Long relBadgePackFullBadgeGymId(RelBadgePack relBadgePack) {
        if ( relBadgePack == null ) {
            return null;
        }
        Badge badge = relBadgePack.getBadge();
        if ( badge == null ) {
            return null;
        }
        Long gymId = badge.getGymId();
        if ( gymId == null ) {
            return null;
        }
        return gymId;
    }

    private String relBadgePackFullPackName(RelBadgePack relBadgePack) {
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

    private Integer relBadgePackFullPackWorkoutSessions(RelBadgePack relBadgePack) {
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

    private Instant relBadgePackFullBadgeLastModifiedDate(RelBadgePack relBadgePack) {
        if ( relBadgePack == null ) {
            return null;
        }
        Badge badge = relBadgePack.getBadge();
        if ( badge == null ) {
            return null;
        }
        Instant lastModifiedDate = badge.getLastModifiedDate();
        if ( lastModifiedDate == null ) {
            return null;
        }
        return lastModifiedDate;
    }

    private Instant relBadgePackFullBadgeCreatedDate(RelBadgePack relBadgePack) {
        if ( relBadgePack == null ) {
            return null;
        }
        Badge badge = relBadgePack.getBadge();
        if ( badge == null ) {
            return null;
        }
        Instant createdDate = badge.getCreatedDate();
        if ( createdDate == null ) {
            return null;
        }
        return createdDate;
    }

    private String relBadgePackFullBadgeCreatedBy(RelBadgePack relBadgePack) {
        if ( relBadgePack == null ) {
            return null;
        }
        Badge badge = relBadgePack.getBadge();
        if ( badge == null ) {
            return null;
        }
        String createdBy = badge.getCreatedBy();
        if ( createdBy == null ) {
            return null;
        }
        return createdBy;
    }

    private String relBadgePackFullBadgeLastModifiedBy(RelBadgePack relBadgePack) {
        if ( relBadgePack == null ) {
            return null;
        }
        Badge badge = relBadgePack.getBadge();
        if ( badge == null ) {
            return null;
        }
        String lastModifiedBy = badge.getLastModifiedBy();
        if ( lastModifiedBy == null ) {
            return null;
        }
        return lastModifiedBy;
    }

    private ExtBadgeDesignationStatus relBadgePackFullExtBadgesDesignationStatus(RelBadgePack relBadgePack) {
        if ( relBadgePack == null ) {
            return null;
        }
        ExtBadgeDesignation extBadgesDesignation = relBadgePack.getExtBadgesDesignation();
        if ( extBadgesDesignation == null ) {
            return null;
        }
        ExtBadgeDesignationStatus status = extBadgesDesignation.getStatus();
        if ( status == null ) {
            return null;
        }
        return status;
    }

    private String relBadgePackFullExtBadgesDesignationUserId(RelBadgePack relBadgePack) {
        if ( relBadgePack == null ) {
            return null;
        }
        ExtBadgeDesignation extBadgesDesignation = relBadgePack.getExtBadgesDesignation();
        if ( extBadgesDesignation == null ) {
            return null;
        }
        String userId = extBadgesDesignation.getUserId();
        if ( userId == null ) {
            return null;
        }
        return userId;
    }
}
