package com.alphalab.service.mapper;

import com.alphalab.domain.RelUserGym;
import com.alphalab.service.dto.RelUserGymDTO;
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
public class RelUserGymMapperImpl implements RelUserGymMapper {

    @Override
    public RelUserGym toEntity(RelUserGymDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RelUserGym relUserGym = new RelUserGym();

        relUserGym.setCreatedBy( dto.getCreatedBy() );
        relUserGym.setCreatedDate( dto.getCreatedDate() );
        relUserGym.setLastModifiedBy( dto.getLastModifiedBy() );
        relUserGym.setLastModifiedDate( dto.getLastModifiedDate() );
        relUserGym.setId( dto.getId() );
        relUserGym.setUserId( dto.getUserId() );
        relUserGym.setGymId( dto.getGymId() );

        return relUserGym;
    }

    @Override
    public RelUserGymDTO toDto(RelUserGym entity) {
        if ( entity == null ) {
            return null;
        }

        RelUserGymDTO relUserGymDTO = new RelUserGymDTO();

        relUserGymDTO.setCreatedBy( entity.getCreatedBy() );
        relUserGymDTO.setCreatedDate( entity.getCreatedDate() );
        relUserGymDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        relUserGymDTO.setLastModifiedDate( entity.getLastModifiedDate() );
        relUserGymDTO.setId( entity.getId() );
        relUserGymDTO.setGymId( entity.getGymId() );
        relUserGymDTO.setUserId( entity.getUserId() );

        return relUserGymDTO;
    }

    @Override
    public List<RelUserGym> toEntity(List<RelUserGymDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<RelUserGym> list = new ArrayList<RelUserGym>( dtoList.size() );
        for ( RelUserGymDTO relUserGymDTO : dtoList ) {
            list.add( toEntity( relUserGymDTO ) );
        }

        return list;
    }

    @Override
    public List<RelUserGymDTO> toDto(List<RelUserGym> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RelUserGymDTO> list = new ArrayList<RelUserGymDTO>( entityList.size() );
        for ( RelUserGym relUserGym : entityList ) {
            list.add( toDto( relUserGym ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(RelUserGym entity, RelUserGymDTO dto) {
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
        if ( dto.getUserId() != null ) {
            entity.setUserId( dto.getUserId() );
        }
        if ( dto.getGymId() != null ) {
            entity.setGymId( dto.getGymId() );
        }
    }
}
