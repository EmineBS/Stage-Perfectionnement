package com.alphalab.service.mapper;

import com.alphalab.domain.Feature;
import com.alphalab.service.dto.FeatureDTO;
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
public class FeatureMapperImpl implements FeatureMapper {

    @Override
    public Feature toEntity(FeatureDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Feature feature = new Feature();

        feature.setCreatedBy( dto.getCreatedBy() );
        feature.setCreatedDate( dto.getCreatedDate() );
        feature.setLastModifiedBy( dto.getLastModifiedBy() );
        feature.setLastModifiedDate( dto.getLastModifiedDate() );
        feature.setId( dto.getId() );
        feature.setName( dto.getName() );
        feature.setDescription( dto.getDescription() );
        feature.setEnabled( dto.getEnabled() );
        feature.setPrice( dto.getPrice() );

        return feature;
    }

    @Override
    public FeatureDTO toDto(Feature entity) {
        if ( entity == null ) {
            return null;
        }

        FeatureDTO featureDTO = new FeatureDTO();

        featureDTO.setCreatedBy( entity.getCreatedBy() );
        featureDTO.setCreatedDate( entity.getCreatedDate() );
        featureDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        featureDTO.setLastModifiedDate( entity.getLastModifiedDate() );
        featureDTO.setId( entity.getId() );
        featureDTO.setName( entity.getName() );
        featureDTO.setDescription( entity.getDescription() );
        featureDTO.setEnabled( entity.getEnabled() );
        featureDTO.setPrice( entity.getPrice() );

        return featureDTO;
    }

    @Override
    public List<Feature> toEntity(List<FeatureDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Feature> list = new ArrayList<Feature>( dtoList.size() );
        for ( FeatureDTO featureDTO : dtoList ) {
            list.add( toEntity( featureDTO ) );
        }

        return list;
    }

    @Override
    public List<FeatureDTO> toDto(List<Feature> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<FeatureDTO> list = new ArrayList<FeatureDTO>( entityList.size() );
        for ( Feature feature : entityList ) {
            list.add( toDto( feature ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Feature entity, FeatureDTO dto) {
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
        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
        if ( dto.getEnabled() != null ) {
            entity.setEnabled( dto.getEnabled() );
        }
        if ( dto.getPrice() != null ) {
            entity.setPrice( dto.getPrice() );
        }
    }
}
