package com.alphalab.service.mapper;

import com.alphalab.domain.Amine;
import com.alphalab.service.dto.AmineDTO;
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
public class AmineMapperImpl implements AmineMapper {

    @Override
    public Amine toEntity(AmineDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Amine amine = new Amine();

        amine.setCreatedBy( dto.getCreatedBy() );
        amine.setCreatedDate( dto.getCreatedDate() );
        amine.setLastModifiedBy( dto.getLastModifiedBy() );
        amine.setLastModifiedDate( dto.getLastModifiedDate() );
        amine.setId( dto.getId() );
        amine.setMyName( dto.getMyName() );
        amine.setFamilyName( dto.getFamilyName() );
        amine.setAge( dto.getAge() );

        return amine;
    }

    @Override
    public AmineDTO toDto(Amine entity) {
        if ( entity == null ) {
            return null;
        }

        AmineDTO amineDTO = new AmineDTO();

        amineDTO.setCreatedBy( entity.getCreatedBy() );
        amineDTO.setCreatedDate( entity.getCreatedDate() );
        amineDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        amineDTO.setLastModifiedDate( entity.getLastModifiedDate() );
        amineDTO.setId( entity.getId() );
        amineDTO.setMyName( entity.getMyName() );
        amineDTO.setFamilyName( entity.getFamilyName() );
        amineDTO.setAge( entity.getAge() );

        return amineDTO;
    }

    @Override
    public List<Amine> toEntity(List<AmineDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Amine> list = new ArrayList<Amine>( dtoList.size() );
        for ( AmineDTO amineDTO : dtoList ) {
            list.add( toEntity( amineDTO ) );
        }

        return list;
    }

    @Override
    public List<AmineDTO> toDto(List<Amine> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<AmineDTO> list = new ArrayList<AmineDTO>( entityList.size() );
        for ( Amine amine : entityList ) {
            list.add( toDto( amine ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Amine entity, AmineDTO dto) {
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
        if ( dto.getMyName() != null ) {
            entity.setMyName( dto.getMyName() );
        }
        if ( dto.getFamilyName() != null ) {
            entity.setFamilyName( dto.getFamilyName() );
        }
        if ( dto.getAge() != null ) {
            entity.setAge( dto.getAge() );
        }
    }
}
