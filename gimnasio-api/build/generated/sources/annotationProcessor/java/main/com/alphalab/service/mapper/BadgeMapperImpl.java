package com.alphalab.service.mapper;

import com.alphalab.domain.Badge;
import com.alphalab.domain.Gym;
import com.alphalab.service.dto.BadgeDTO;
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
public class BadgeMapperImpl implements BadgeMapper {

    @Override
    public Badge toEntity(BadgeDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Badge badge = new Badge();

        badge.setCreatedBy( dto.getCreatedBy() );
        badge.setCreatedDate( dto.getCreatedDate() );
        badge.setLastModifiedBy( dto.getLastModifiedBy() );
        badge.setLastModifiedDate( dto.getLastModifiedDate() );
        badge.setId( dto.getId() );
        badge.setUid( dto.getUid() );
        badge.setStatus( dto.getStatus() );
        badge.setEnabled( dto.getEnabled() );
        badge.setGymId( dto.getGymId() );

        return badge;
    }

    @Override
    public List<Badge> toEntity(List<BadgeDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Badge> list = new ArrayList<Badge>( dtoList.size() );
        for ( BadgeDTO badgeDTO : dtoList ) {
            list.add( toEntity( badgeDTO ) );
        }

        return list;
    }

    @Override
    public List<BadgeDTO> toDto(List<Badge> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<BadgeDTO> list = new ArrayList<BadgeDTO>( entityList.size() );
        for ( Badge badge : entityList ) {
            list.add( toDto( badge ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Badge entity, BadgeDTO dto) {
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
        if ( dto.getUid() != null ) {
            entity.setUid( dto.getUid() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( dto.getEnabled() != null ) {
            entity.setEnabled( dto.getEnabled() );
        }
        if ( dto.getGymId() != null ) {
            entity.setGymId( dto.getGymId() );
        }
    }

    @Override
    public BadgeDTO toDto(Badge badge) {
        if ( badge == null ) {
            return null;
        }

        BadgeDTO badgeDTO = new BadgeDTO();

        badgeDTO.setGymName( badgeGymName( badge ) );
        badgeDTO.setCreatedBy( badge.getCreatedBy() );
        badgeDTO.setCreatedDate( badge.getCreatedDate() );
        badgeDTO.setLastModifiedBy( badge.getLastModifiedBy() );
        badgeDTO.setLastModifiedDate( badge.getLastModifiedDate() );
        badgeDTO.setId( badge.getId() );
        badgeDTO.setUid( badge.getUid() );
        badgeDTO.setStatus( badge.getStatus() );
        badgeDTO.setEnabled( badge.getEnabled() );
        badgeDTO.setGymId( badge.getGymId() );

        return badgeDTO;
    }

    private String badgeGymName(Badge badge) {
        if ( badge == null ) {
            return null;
        }
        Gym gym = badge.getGym();
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
