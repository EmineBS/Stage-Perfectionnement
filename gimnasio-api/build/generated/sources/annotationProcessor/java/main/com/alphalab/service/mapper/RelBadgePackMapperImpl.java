package com.alphalab.service.mapper;

import com.alphalab.domain.RelBadgePack;
import com.alphalab.service.dto.RelBadgePackDTO;
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
public class RelBadgePackMapperImpl implements RelBadgePackMapper {

    @Override
    public RelBadgePack toEntity(RelBadgePackDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RelBadgePack relBadgePack = new RelBadgePack();

        relBadgePack.setCreatedBy( dto.getCreatedBy() );
        relBadgePack.setCreatedDate( dto.getCreatedDate() );
        relBadgePack.setLastModifiedBy( dto.getLastModifiedBy() );
        relBadgePack.setLastModifiedDate( dto.getLastModifiedDate() );
        relBadgePack.setId( dto.getId() );
        relBadgePack.setEnabled( dto.getEnabled() );
        relBadgePack.setStatus( dto.getStatus() );
        relBadgePack.badge( dto.getBadge() );
        relBadgePack.pack( dto.getPack() );

        return relBadgePack;
    }

    @Override
    public RelBadgePackDTO toDto(RelBadgePack entity) {
        if ( entity == null ) {
            return null;
        }

        RelBadgePackDTO relBadgePackDTO = new RelBadgePackDTO();

        relBadgePackDTO.setCreatedBy( entity.getCreatedBy() );
        relBadgePackDTO.setCreatedDate( entity.getCreatedDate() );
        relBadgePackDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        relBadgePackDTO.setLastModifiedDate( entity.getLastModifiedDate() );
        relBadgePackDTO.setId( entity.getId() );
        relBadgePackDTO.setEnabled( entity.getEnabled() );
        relBadgePackDTO.setStatus( entity.getStatus() );
        relBadgePackDTO.setPack( entity.getPack() );
        relBadgePackDTO.setBadge( entity.getBadge() );

        return relBadgePackDTO;
    }

    @Override
    public List<RelBadgePack> toEntity(List<RelBadgePackDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<RelBadgePack> list = new ArrayList<RelBadgePack>( dtoList.size() );
        for ( RelBadgePackDTO relBadgePackDTO : dtoList ) {
            list.add( toEntity( relBadgePackDTO ) );
        }

        return list;
    }

    @Override
    public List<RelBadgePackDTO> toDto(List<RelBadgePack> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RelBadgePackDTO> list = new ArrayList<RelBadgePackDTO>( entityList.size() );
        for ( RelBadgePack relBadgePack : entityList ) {
            list.add( toDto( relBadgePack ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(RelBadgePack entity, RelBadgePackDTO dto) {
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
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( dto.getBadge() != null ) {
            entity.badge( dto.getBadge() );
        }
        if ( dto.getPack() != null ) {
            entity.pack( dto.getPack() );
        }
    }
}
