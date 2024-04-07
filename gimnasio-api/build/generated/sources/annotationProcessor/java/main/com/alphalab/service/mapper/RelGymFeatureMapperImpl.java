package com.alphalab.service.mapper;

import com.alphalab.domain.Feature;
import com.alphalab.domain.RelGymFeature;
import com.alphalab.service.dto.RelGymFeatureDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-27T06:58:22+0100",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class RelGymFeatureMapperImpl implements RelGymFeatureMapper {

    @Override
    public RelGymFeature toEntity(RelGymFeatureDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RelGymFeature relGymFeature = new RelGymFeature();

        relGymFeature.setCreatedBy( dto.getCreatedBy() );
        relGymFeature.setCreatedDate( dto.getCreatedDate() );
        relGymFeature.setLastModifiedBy( dto.getLastModifiedBy() );
        relGymFeature.setLastModifiedDate( dto.getLastModifiedDate() );
        relGymFeature.setId( dto.getId() );
        relGymFeature.setFeatureId( dto.getFeatureId() );
        relGymFeature.setGymId( dto.getGymId() );

        return relGymFeature;
    }

    @Override
    public List<RelGymFeature> toEntity(List<RelGymFeatureDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<RelGymFeature> list = new ArrayList<RelGymFeature>( dtoList.size() );
        for ( RelGymFeatureDTO relGymFeatureDTO : dtoList ) {
            list.add( toEntity( relGymFeatureDTO ) );
        }

        return list;
    }

    @Override
    public List<RelGymFeatureDTO> toDto(List<RelGymFeature> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RelGymFeatureDTO> list = new ArrayList<RelGymFeatureDTO>( entityList.size() );
        for ( RelGymFeature relGymFeature : entityList ) {
            list.add( toDto( relGymFeature ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(RelGymFeature entity, RelGymFeatureDTO dto) {
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
        if ( dto.getFeatureId() != null ) {
            entity.setFeatureId( dto.getFeatureId() );
        }
        if ( dto.getGymId() != null ) {
            entity.setGymId( dto.getGymId() );
        }
    }

    @Override
    public RelGymFeatureDTO toDto(RelGymFeature relGymFeature) {
        if ( relGymFeature == null ) {
            return null;
        }

        RelGymFeatureDTO relGymFeatureDTO = new RelGymFeatureDTO();

        relGymFeatureDTO.setFeatureName( relGymFeatureFeatureName( relGymFeature ) );
        relGymFeatureDTO.setCreatedBy( relGymFeature.getCreatedBy() );
        relGymFeatureDTO.setCreatedDate( relGymFeature.getCreatedDate() );
        relGymFeatureDTO.setLastModifiedBy( relGymFeature.getLastModifiedBy() );
        relGymFeatureDTO.setLastModifiedDate( relGymFeature.getLastModifiedDate() );
        relGymFeatureDTO.setId( relGymFeature.getId() );
        relGymFeatureDTO.setGymId( relGymFeature.getGymId() );
        relGymFeatureDTO.setFeatureId( relGymFeature.getFeatureId() );

        return relGymFeatureDTO;
    }

    private String relGymFeatureFeatureName(RelGymFeature relGymFeature) {
        if ( relGymFeature == null ) {
            return null;
        }
        Feature feature = relGymFeature.getFeature();
        if ( feature == null ) {
            return null;
        }
        String name = feature.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
