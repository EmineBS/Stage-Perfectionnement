package com.alphalab.service.mapper;

import com.alphalab.domain.Badge;
import com.alphalab.domain.OffrePack;
import com.alphalab.domain.Profile;
import com.alphalab.domain.RDV;
import com.alphalab.domain.RelBadgePack;
import com.alphalab.domain.enumeration.BadgePackStatus;
import com.alphalab.service.dto.RDVDTO;
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
public class RDVMapperImpl implements RDVMapper {

    @Override
    public RDV toEntity(RDVDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RDV rDV = new RDV();

        rDV.setCreatedBy( dto.getCreatedBy() );
        rDV.setCreatedDate( dto.getCreatedDate() );
        rDV.setLastModifiedBy( dto.getLastModifiedBy() );
        rDV.setLastModifiedDate( dto.getLastModifiedDate() );
        rDV.setId( dto.getId() );
        rDV.setFromDate( dto.getFromDate() );
        rDV.setToDate( dto.getToDate() );
        rDV.setEnabled( dto.getEnabled() );
        rDV.setStatus( dto.getStatus() );
        rDV.setCalendarId( dto.getCalendarId() );
        rDV.setRelBadgePackId( dto.getRelBadgePackId() );

        return rDV;
    }

    @Override
    public List<RDV> toEntity(List<RDVDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<RDV> list = new ArrayList<RDV>( dtoList.size() );
        for ( RDVDTO rDVDTO : dtoList ) {
            list.add( toEntity( rDVDTO ) );
        }

        return list;
    }

    @Override
    public List<RDVDTO> toDto(List<RDV> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RDVDTO> list = new ArrayList<RDVDTO>( entityList.size() );
        for ( RDV rDV : entityList ) {
            list.add( toDto( rDV ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(RDV entity, RDVDTO dto) {
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
        if ( dto.getFromDate() != null ) {
            entity.setFromDate( dto.getFromDate() );
        }
        if ( dto.getToDate() != null ) {
            entity.setToDate( dto.getToDate() );
        }
        if ( dto.getEnabled() != null ) {
            entity.setEnabled( dto.getEnabled() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( dto.getCalendarId() != null ) {
            entity.setCalendarId( dto.getCalendarId() );
        }
        if ( dto.getRelBadgePackId() != null ) {
            entity.setRelBadgePackId( dto.getRelBadgePackId() );
        }
    }

    @Override
    public RDVDTO toDto(RDV rdv) {
        if ( rdv == null ) {
            return null;
        }

        RDVDTO rDVDTO = new RDVDTO();

        rDVDTO.setBadgePackStatus( rdvRelBadgePackStatus( rdv ) );
        rDVDTO.setBadgeId( rdvRelBadgePackBadgeId( rdv ) );
        rDVDTO.setBadgeUid( rdvRelBadgePackBadgeUid( rdv ) );
        Long id1 = rdvRelBadgePackPackId( rdv );
        if ( id1 != null ) {
            rDVDTO.setPackId( String.valueOf( id1 ) );
        }
        rDVDTO.setPackName( rdvRelBadgePackPackName( rdv ) );
        rDVDTO.setProfileEmail( rdvProfileEmail( rdv ) );
        rDVDTO.setCreatedBy( rdv.getCreatedBy() );
        rDVDTO.setCreatedDate( rdv.getCreatedDate() );
        rDVDTO.setLastModifiedBy( rdv.getLastModifiedBy() );
        rDVDTO.setLastModifiedDate( rdv.getLastModifiedDate() );
        rDVDTO.setId( rdv.getId() );
        rDVDTO.setFromDate( rdv.getFromDate() );
        rDVDTO.setToDate( rdv.getToDate() );
        rDVDTO.setEnabled( rdv.getEnabled() );
        rDVDTO.setStatus( rdv.getStatus() );
        rDVDTO.setCalendarId( rdv.getCalendarId() );
        rDVDTO.setRelBadgePackId( rdv.getRelBadgePackId() );

        return rDVDTO;
    }

    private BadgePackStatus rdvRelBadgePackStatus(RDV rDV) {
        if ( rDV == null ) {
            return null;
        }
        RelBadgePack relBadgePack = rDV.getRelBadgePack();
        if ( relBadgePack == null ) {
            return null;
        }
        BadgePackStatus status = relBadgePack.getStatus();
        if ( status == null ) {
            return null;
        }
        return status;
    }

    private Long rdvRelBadgePackBadgeId(RDV rDV) {
        if ( rDV == null ) {
            return null;
        }
        RelBadgePack relBadgePack = rDV.getRelBadgePack();
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

    private String rdvRelBadgePackBadgeUid(RDV rDV) {
        if ( rDV == null ) {
            return null;
        }
        RelBadgePack relBadgePack = rDV.getRelBadgePack();
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

    private Long rdvRelBadgePackPackId(RDV rDV) {
        if ( rDV == null ) {
            return null;
        }
        RelBadgePack relBadgePack = rDV.getRelBadgePack();
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

    private String rdvRelBadgePackPackName(RDV rDV) {
        if ( rDV == null ) {
            return null;
        }
        RelBadgePack relBadgePack = rDV.getRelBadgePack();
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

    private String rdvProfileEmail(RDV rDV) {
        if ( rDV == null ) {
            return null;
        }
        Profile profile = rDV.getProfile();
        if ( profile == null ) {
            return null;
        }
        String email = profile.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }
}
