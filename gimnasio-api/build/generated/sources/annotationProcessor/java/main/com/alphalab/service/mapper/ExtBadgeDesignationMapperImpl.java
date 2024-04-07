package com.alphalab.service.mapper;

import com.alphalab.domain.ExtBadgeDesignation;
import com.alphalab.service.dto.ExtBadgeDesignationDTO;
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
public class ExtBadgeDesignationMapperImpl implements ExtBadgeDesignationMapper {

    @Override
    public ExtBadgeDesignation toEntity(ExtBadgeDesignationDTO dto) {
        if ( dto == null ) {
            return null;
        }

        ExtBadgeDesignation extBadgeDesignation = new ExtBadgeDesignation();

        extBadgeDesignation.setCreatedBy( dto.getCreatedBy() );
        extBadgeDesignation.setCreatedDate( dto.getCreatedDate() );
        extBadgeDesignation.setLastModifiedBy( dto.getLastModifiedBy() );
        extBadgeDesignation.setLastModifiedDate( dto.getLastModifiedDate() );
        extBadgeDesignation.setId( dto.getId() );
        extBadgeDesignation.setStatus( dto.getStatus() );
        extBadgeDesignation.setBadgeId( dto.getBadgeId() );
        extBadgeDesignation.setUserId( dto.getUserId() );

        return extBadgeDesignation;
    }

    @Override
    public ExtBadgeDesignationDTO toDto(ExtBadgeDesignation entity) {
        if ( entity == null ) {
            return null;
        }

        ExtBadgeDesignationDTO extBadgeDesignationDTO = new ExtBadgeDesignationDTO();

        extBadgeDesignationDTO.setCreatedBy( entity.getCreatedBy() );
        extBadgeDesignationDTO.setCreatedDate( entity.getCreatedDate() );
        extBadgeDesignationDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        extBadgeDesignationDTO.setLastModifiedDate( entity.getLastModifiedDate() );
        extBadgeDesignationDTO.setId( entity.getId() );
        extBadgeDesignationDTO.setStatus( entity.getStatus() );
        extBadgeDesignationDTO.setBadgeId( entity.getBadgeId() );
        extBadgeDesignationDTO.setUserId( entity.getUserId() );

        return extBadgeDesignationDTO;
    }

    @Override
    public List<ExtBadgeDesignation> toEntity(List<ExtBadgeDesignationDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<ExtBadgeDesignation> list = new ArrayList<ExtBadgeDesignation>( dtoList.size() );
        for ( ExtBadgeDesignationDTO extBadgeDesignationDTO : dtoList ) {
            list.add( toEntity( extBadgeDesignationDTO ) );
        }

        return list;
    }

    @Override
    public List<ExtBadgeDesignationDTO> toDto(List<ExtBadgeDesignation> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ExtBadgeDesignationDTO> list = new ArrayList<ExtBadgeDesignationDTO>( entityList.size() );
        for ( ExtBadgeDesignation extBadgeDesignation : entityList ) {
            list.add( toDto( extBadgeDesignation ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(ExtBadgeDesignation entity, ExtBadgeDesignationDTO dto) {
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
        if ( dto.getBadgeId() != null ) {
            entity.setBadgeId( dto.getBadgeId() );
        }
        if ( dto.getUserId() != null ) {
            entity.setUserId( dto.getUserId() );
        }
    }
}
