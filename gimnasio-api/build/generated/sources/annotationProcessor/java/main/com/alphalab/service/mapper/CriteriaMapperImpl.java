package com.alphalab.service.mapper;

import com.alphalab.domain.Criteria;
import com.alphalab.domain.Gym;
import com.alphalab.service.dto.CriteriaDTO;
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
public class CriteriaMapperImpl implements CriteriaMapper {

    @Override
    public Criteria toEntity(CriteriaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Criteria criteria = new Criteria();

        criteria.setCreatedBy( dto.getCreatedBy() );
        criteria.setCreatedDate( dto.getCreatedDate() );
        criteria.setLastModifiedBy( dto.getLastModifiedBy() );
        criteria.setLastModifiedDate( dto.getLastModifiedDate() );
        criteria.setId( dto.getId() );
        criteria.setName( dto.getName() );
        criteria.setDescription( dto.getDescription() );
        criteria.setDisplay( dto.getDisplay() );
        criteria.setEnabled( dto.getEnabled() );
        criteria.setGymId( dto.getGymId() );

        return criteria;
    }

    @Override
    public List<Criteria> toEntity(List<CriteriaDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Criteria> list = new ArrayList<Criteria>( dtoList.size() );
        for ( CriteriaDTO criteriaDTO : dtoList ) {
            list.add( toEntity( criteriaDTO ) );
        }

        return list;
    }

    @Override
    public List<CriteriaDTO> toDto(List<Criteria> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<CriteriaDTO> list = new ArrayList<CriteriaDTO>( entityList.size() );
        for ( Criteria criteria : entityList ) {
            list.add( toDto( criteria ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Criteria entity, CriteriaDTO dto) {
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
        if ( dto.getDisplay() != null ) {
            entity.setDisplay( dto.getDisplay() );
        }
        if ( dto.getEnabled() != null ) {
            entity.setEnabled( dto.getEnabled() );
        }
        if ( dto.getGymId() != null ) {
            entity.setGymId( dto.getGymId() );
        }
    }

    @Override
    public CriteriaDTO toDto(Criteria criteria) {
        if ( criteria == null ) {
            return null;
        }

        CriteriaDTO criteriaDTO = new CriteriaDTO();

        criteriaDTO.setGymName( criteriaGymName( criteria ) );
        criteriaDTO.setCreatedBy( criteria.getCreatedBy() );
        criteriaDTO.setCreatedDate( criteria.getCreatedDate() );
        criteriaDTO.setLastModifiedBy( criteria.getLastModifiedBy() );
        criteriaDTO.setLastModifiedDate( criteria.getLastModifiedDate() );
        criteriaDTO.setId( criteria.getId() );
        criteriaDTO.setName( criteria.getName() );
        criteriaDTO.setDescription( criteria.getDescription() );
        criteriaDTO.setEnabled( criteria.getEnabled() );
        criteriaDTO.setDisplay( criteria.getDisplay() );
        criteriaDTO.setGymId( criteria.getGymId() );

        return criteriaDTO;
    }

    private String criteriaGymName(Criteria criteria) {
        if ( criteria == null ) {
            return null;
        }
        Gym gym = criteria.getGym();
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
