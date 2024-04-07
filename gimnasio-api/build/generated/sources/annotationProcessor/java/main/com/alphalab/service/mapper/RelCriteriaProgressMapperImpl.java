package com.alphalab.service.mapper;

import com.alphalab.domain.RelCriteriaProgress;
import com.alphalab.service.dto.RelCriteriaProgressDTO;
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
public class RelCriteriaProgressMapperImpl implements RelCriteriaProgressMapper {

    @Override
    public RelCriteriaProgress toEntity(RelCriteriaProgressDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RelCriteriaProgress relCriteriaProgress = new RelCriteriaProgress();

        relCriteriaProgress.setCreatedBy( dto.getCreatedBy() );
        relCriteriaProgress.setCreatedDate( dto.getCreatedDate() );
        relCriteriaProgress.setLastModifiedBy( dto.getLastModifiedBy() );
        relCriteriaProgress.setLastModifiedDate( dto.getLastModifiedDate() );
        relCriteriaProgress.setId( dto.getId() );
        relCriteriaProgress.setValue( dto.getValue() );
        relCriteriaProgress.setCriteriaId( dto.getCriteriaId() );
        relCriteriaProgress.setProgressId( dto.getProgressId() );

        return relCriteriaProgress;
    }

    @Override
    public RelCriteriaProgressDTO toDto(RelCriteriaProgress entity) {
        if ( entity == null ) {
            return null;
        }

        RelCriteriaProgressDTO relCriteriaProgressDTO = new RelCriteriaProgressDTO();

        relCriteriaProgressDTO.setCreatedBy( entity.getCreatedBy() );
        relCriteriaProgressDTO.setCreatedDate( entity.getCreatedDate() );
        relCriteriaProgressDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        relCriteriaProgressDTO.setLastModifiedDate( entity.getLastModifiedDate() );
        relCriteriaProgressDTO.setId( entity.getId() );
        relCriteriaProgressDTO.setValue( entity.getValue() );
        relCriteriaProgressDTO.setCriteriaId( entity.getCriteriaId() );
        relCriteriaProgressDTO.setProgressId( entity.getProgressId() );

        return relCriteriaProgressDTO;
    }

    @Override
    public List<RelCriteriaProgress> toEntity(List<RelCriteriaProgressDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<RelCriteriaProgress> list = new ArrayList<RelCriteriaProgress>( dtoList.size() );
        for ( RelCriteriaProgressDTO relCriteriaProgressDTO : dtoList ) {
            list.add( toEntity( relCriteriaProgressDTO ) );
        }

        return list;
    }

    @Override
    public List<RelCriteriaProgressDTO> toDto(List<RelCriteriaProgress> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RelCriteriaProgressDTO> list = new ArrayList<RelCriteriaProgressDTO>( entityList.size() );
        for ( RelCriteriaProgress relCriteriaProgress : entityList ) {
            list.add( toDto( relCriteriaProgress ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(RelCriteriaProgress entity, RelCriteriaProgressDTO dto) {
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
        if ( dto.getValue() != null ) {
            entity.setValue( dto.getValue() );
        }
        if ( dto.getCriteriaId() != null ) {
            entity.setCriteriaId( dto.getCriteriaId() );
        }
        if ( dto.getProgressId() != null ) {
            entity.setProgressId( dto.getProgressId() );
        }
    }
}
