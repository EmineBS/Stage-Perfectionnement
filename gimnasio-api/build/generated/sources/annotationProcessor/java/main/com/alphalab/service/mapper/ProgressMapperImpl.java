package com.alphalab.service.mapper;

import com.alphalab.domain.Progress;
import com.alphalab.service.dto.ProgressDTO;
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
public class ProgressMapperImpl implements ProgressMapper {

    @Override
    public Progress toEntity(ProgressDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Progress progress = new Progress();

        progress.setCreatedBy( dto.getCreatedBy() );
        progress.setCreatedDate( dto.getCreatedDate() );
        progress.setLastModifiedBy( dto.getLastModifiedBy() );
        progress.setLastModifiedDate( dto.getLastModifiedDate() );
        progress.setId( dto.getId() );
        progress.setBadgeId( dto.getBadgeId() );

        return progress;
    }

    @Override
    public ProgressDTO toDto(Progress entity) {
        if ( entity == null ) {
            return null;
        }

        ProgressDTO progressDTO = new ProgressDTO();

        progressDTO.setCreatedBy( entity.getCreatedBy() );
        progressDTO.setCreatedDate( entity.getCreatedDate() );
        progressDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        progressDTO.setLastModifiedDate( entity.getLastModifiedDate() );
        progressDTO.setId( entity.getId() );
        progressDTO.setBadgeId( entity.getBadgeId() );

        return progressDTO;
    }

    @Override
    public List<Progress> toEntity(List<ProgressDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Progress> list = new ArrayList<Progress>( dtoList.size() );
        for ( ProgressDTO progressDTO : dtoList ) {
            list.add( toEntity( progressDTO ) );
        }

        return list;
    }

    @Override
    public List<ProgressDTO> toDto(List<Progress> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ProgressDTO> list = new ArrayList<ProgressDTO>( entityList.size() );
        for ( Progress progress : entityList ) {
            list.add( toDto( progress ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Progress entity, ProgressDTO dto) {
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
        if ( dto.getBadgeId() != null ) {
            entity.setBadgeId( dto.getBadgeId() );
        }
    }
}
