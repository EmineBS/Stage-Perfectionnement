package com.alphalab.service.mapper;

import com.alphalab.domain.Feature;
import com.alphalab.domain.Gym;
import com.alphalab.service.dto.GymDTO;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-27T06:58:24+0100",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class GymMapperImpl implements GymMapper {

    @Override
    public Gym toEntity(GymDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Gym gym = new Gym();

        gym.setCreatedBy( dto.getCreatedBy() );
        gym.setCreatedDate( dto.getCreatedDate() );
        gym.setLastModifiedBy( dto.getLastModifiedBy() );
        gym.setLastModifiedDate( dto.getLastModifiedDate() );
        gym.setId( dto.getId() );
        gym.setName( dto.getName() );
        gym.setDescription( dto.getDescription() );
        gym.setBadgeAmount( dto.getBadgeAmount() );
        gym.setLocation( dto.getLocation() );
        gym.setPhoneNumber( dto.getPhoneNumber() );
        gym.setEmail( dto.getEmail() );
        gym.setRegistrationNumber( dto.getRegistrationNumber() );
        gym.setStatus( dto.getStatus() );
        Set<Feature> set = dto.getFeatures();
        if ( set != null ) {
            gym.setFeatures( new LinkedHashSet<Feature>( set ) );
        }

        return gym;
    }

    @Override
    public GymDTO toDto(Gym entity) {
        if ( entity == null ) {
            return null;
        }

        GymDTO gymDTO = new GymDTO();

        gymDTO.setCreatedBy( entity.getCreatedBy() );
        gymDTO.setCreatedDate( entity.getCreatedDate() );
        gymDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        gymDTO.setLastModifiedDate( entity.getLastModifiedDate() );
        gymDTO.setId( entity.getId() );
        gymDTO.setName( entity.getName() );
        gymDTO.setDescription( entity.getDescription() );
        gymDTO.setLocation( entity.getLocation() );
        gymDTO.setPhoneNumber( entity.getPhoneNumber() );
        gymDTO.setEmail( entity.getEmail() );
        gymDTO.setRegistrationNumber( entity.getRegistrationNumber() );
        gymDTO.setBadgeAmount( entity.getBadgeAmount() );
        gymDTO.setStatus( entity.getStatus() );
        Set<Feature> set = entity.getFeatures();
        if ( set != null ) {
            gymDTO.setFeatures( new LinkedHashSet<Feature>( set ) );
        }

        return gymDTO;
    }

    @Override
    public List<Gym> toEntity(List<GymDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Gym> list = new ArrayList<Gym>( dtoList.size() );
        for ( GymDTO gymDTO : dtoList ) {
            list.add( toEntity( gymDTO ) );
        }

        return list;
    }

    @Override
    public List<GymDTO> toDto(List<Gym> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<GymDTO> list = new ArrayList<GymDTO>( entityList.size() );
        for ( Gym gym : entityList ) {
            list.add( toDto( gym ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Gym entity, GymDTO dto) {
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
        if ( dto.getBadgeAmount() != null ) {
            entity.setBadgeAmount( dto.getBadgeAmount() );
        }
        if ( dto.getLocation() != null ) {
            entity.setLocation( dto.getLocation() );
        }
        if ( dto.getPhoneNumber() != null ) {
            entity.setPhoneNumber( dto.getPhoneNumber() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getRegistrationNumber() != null ) {
            entity.setRegistrationNumber( dto.getRegistrationNumber() );
        }
        if ( dto.getStatus() != null ) {
            entity.setStatus( dto.getStatus() );
        }
        if ( entity.getFeatures() != null ) {
            Set<Feature> set = dto.getFeatures();
            if ( set != null ) {
                entity.getFeatures().clear();
                entity.getFeatures().addAll( set );
            }
        }
        else {
            Set<Feature> set = dto.getFeatures();
            if ( set != null ) {
                entity.setFeatures( new LinkedHashSet<Feature>( set ) );
            }
        }
    }
}
