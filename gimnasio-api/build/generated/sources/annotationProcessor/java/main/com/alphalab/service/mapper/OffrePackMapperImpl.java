package com.alphalab.service.mapper;

import com.alphalab.domain.Feature;
import com.alphalab.domain.Gym;
import com.alphalab.domain.OffrePack;
import com.alphalab.service.dto.GymDTO;
import com.alphalab.service.dto.OffrePackDTO;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-27T06:58:23+0100",
    comments = "version: 1.5.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.2.jar, environment: Java 17.0.9 (Oracle Corporation)"
)
@Component
public class OffrePackMapperImpl implements OffrePackMapper {

    @Override
    public OffrePackDTO toDto(OffrePack entity) {
        if ( entity == null ) {
            return null;
        }

        OffrePackDTO offrePackDTO = new OffrePackDTO();

        offrePackDTO.setCreatedBy( entity.getCreatedBy() );
        offrePackDTO.setCreatedDate( entity.getCreatedDate() );
        offrePackDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        offrePackDTO.setLastModifiedDate( entity.getLastModifiedDate() );
        offrePackDTO.setId( entity.getId() );
        offrePackDTO.setName( entity.getName() );
        offrePackDTO.setDescription( entity.getDescription() );
        offrePackDTO.setWorkoutSessions( entity.getWorkoutSessions() );
        offrePackDTO.setPrice( entity.getPrice() );
        offrePackDTO.setAutoConfirm( entity.getAutoConfirm() );
        offrePackDTO.setRdvEnabled( entity.getRdvEnabled() );
        offrePackDTO.setGym( gymToGymDTO( entity.getGym() ) );

        return offrePackDTO;
    }

    @Override
    public List<OffrePack> toEntity(List<OffrePackDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<OffrePack> list = new ArrayList<OffrePack>( dtoList.size() );
        for ( OffrePackDTO offrePackDTO : dtoList ) {
            list.add( toEntity( offrePackDTO ) );
        }

        return list;
    }

    @Override
    public List<OffrePackDTO> toDto(List<OffrePack> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<OffrePackDTO> list = new ArrayList<OffrePackDTO>( entityList.size() );
        for ( OffrePack offrePack : entityList ) {
            list.add( toDto( offrePack ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(OffrePack entity, OffrePackDTO dto) {
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
        if ( dto.getWorkoutSessions() != null ) {
            entity.setWorkoutSessions( dto.getWorkoutSessions() );
        }
        if ( dto.getPrice() != null ) {
            entity.setPrice( dto.getPrice() );
        }
        if ( dto.getAutoConfirm() != null ) {
            entity.setAutoConfirm( dto.getAutoConfirm() );
        }
        if ( dto.getRdvEnabled() != null ) {
            entity.setRdvEnabled( dto.getRdvEnabled() );
        }
        if ( dto.getGym() != null ) {
            if ( entity.getGym() == null ) {
                entity.setGym( new Gym() );
            }
            gymDTOToGym( dto.getGym(), entity.getGym() );
        }
    }

    @Override
    public OffrePack toEntity(OffrePackDTO offrePackDTO) {
        if ( offrePackDTO == null ) {
            return null;
        }

        OffrePack offrePack = new OffrePack();

        offrePack.setGymId( offrePackDTOGymId( offrePackDTO ) );
        offrePack.setCreatedBy( offrePackDTO.getCreatedBy() );
        offrePack.setCreatedDate( offrePackDTO.getCreatedDate() );
        offrePack.setLastModifiedBy( offrePackDTO.getLastModifiedBy() );
        offrePack.setLastModifiedDate( offrePackDTO.getLastModifiedDate() );
        offrePack.setId( offrePackDTO.getId() );
        offrePack.setName( offrePackDTO.getName() );
        offrePack.setDescription( offrePackDTO.getDescription() );
        offrePack.setWorkoutSessions( offrePackDTO.getWorkoutSessions() );
        offrePack.setPrice( offrePackDTO.getPrice() );
        offrePack.setAutoConfirm( offrePackDTO.getAutoConfirm() );
        offrePack.setRdvEnabled( offrePackDTO.getRdvEnabled() );
        offrePack.setGym( gymDTOToGym1( offrePackDTO.getGym() ) );

        return offrePack;
    }

    protected GymDTO gymToGymDTO(Gym gym) {
        if ( gym == null ) {
            return null;
        }

        GymDTO gymDTO = new GymDTO();

        gymDTO.setCreatedBy( gym.getCreatedBy() );
        gymDTO.setCreatedDate( gym.getCreatedDate() );
        gymDTO.setLastModifiedBy( gym.getLastModifiedBy() );
        gymDTO.setLastModifiedDate( gym.getLastModifiedDate() );
        gymDTO.setId( gym.getId() );
        gymDTO.setName( gym.getName() );
        gymDTO.setDescription( gym.getDescription() );
        gymDTO.setLocation( gym.getLocation() );
        gymDTO.setPhoneNumber( gym.getPhoneNumber() );
        gymDTO.setEmail( gym.getEmail() );
        gymDTO.setRegistrationNumber( gym.getRegistrationNumber() );
        gymDTO.setBadgeAmount( gym.getBadgeAmount() );
        gymDTO.setStatus( gym.getStatus() );
        Set<Feature> set = gym.getFeatures();
        if ( set != null ) {
            gymDTO.setFeatures( new LinkedHashSet<Feature>( set ) );
        }

        return gymDTO;
    }

    protected void gymDTOToGym(GymDTO gymDTO, Gym mappingTarget) {
        if ( gymDTO == null ) {
            return;
        }

        if ( gymDTO.getCreatedBy() != null ) {
            mappingTarget.setCreatedBy( gymDTO.getCreatedBy() );
        }
        if ( gymDTO.getCreatedDate() != null ) {
            mappingTarget.setCreatedDate( gymDTO.getCreatedDate() );
        }
        if ( gymDTO.getLastModifiedBy() != null ) {
            mappingTarget.setLastModifiedBy( gymDTO.getLastModifiedBy() );
        }
        if ( gymDTO.getLastModifiedDate() != null ) {
            mappingTarget.setLastModifiedDate( gymDTO.getLastModifiedDate() );
        }
        if ( gymDTO.getId() != null ) {
            mappingTarget.setId( gymDTO.getId() );
        }
        if ( gymDTO.getName() != null ) {
            mappingTarget.setName( gymDTO.getName() );
        }
        if ( gymDTO.getDescription() != null ) {
            mappingTarget.setDescription( gymDTO.getDescription() );
        }
        if ( gymDTO.getBadgeAmount() != null ) {
            mappingTarget.setBadgeAmount( gymDTO.getBadgeAmount() );
        }
        if ( gymDTO.getLocation() != null ) {
            mappingTarget.setLocation( gymDTO.getLocation() );
        }
        if ( gymDTO.getPhoneNumber() != null ) {
            mappingTarget.setPhoneNumber( gymDTO.getPhoneNumber() );
        }
        if ( gymDTO.getEmail() != null ) {
            mappingTarget.setEmail( gymDTO.getEmail() );
        }
        if ( gymDTO.getRegistrationNumber() != null ) {
            mappingTarget.setRegistrationNumber( gymDTO.getRegistrationNumber() );
        }
        if ( gymDTO.getStatus() != null ) {
            mappingTarget.setStatus( gymDTO.getStatus() );
        }
        if ( mappingTarget.getFeatures() != null ) {
            Set<Feature> set = gymDTO.getFeatures();
            if ( set != null ) {
                mappingTarget.getFeatures().clear();
                mappingTarget.getFeatures().addAll( set );
            }
        }
        else {
            Set<Feature> set = gymDTO.getFeatures();
            if ( set != null ) {
                mappingTarget.setFeatures( new LinkedHashSet<Feature>( set ) );
            }
        }
    }

    private Long offrePackDTOGymId(OffrePackDTO offrePackDTO) {
        if ( offrePackDTO == null ) {
            return null;
        }
        GymDTO gym = offrePackDTO.getGym();
        if ( gym == null ) {
            return null;
        }
        Long id = gym.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected Gym gymDTOToGym1(GymDTO gymDTO) {
        if ( gymDTO == null ) {
            return null;
        }

        Gym gym = new Gym();

        gym.setCreatedBy( gymDTO.getCreatedBy() );
        gym.setCreatedDate( gymDTO.getCreatedDate() );
        gym.setLastModifiedBy( gymDTO.getLastModifiedBy() );
        gym.setLastModifiedDate( gymDTO.getLastModifiedDate() );
        gym.setId( gymDTO.getId() );
        gym.setName( gymDTO.getName() );
        gym.setDescription( gymDTO.getDescription() );
        gym.setBadgeAmount( gymDTO.getBadgeAmount() );
        gym.setLocation( gymDTO.getLocation() );
        gym.setPhoneNumber( gymDTO.getPhoneNumber() );
        gym.setEmail( gymDTO.getEmail() );
        gym.setRegistrationNumber( gymDTO.getRegistrationNumber() );
        gym.setStatus( gymDTO.getStatus() );
        Set<Feature> set = gymDTO.getFeatures();
        if ( set != null ) {
            gym.setFeatures( new LinkedHashSet<Feature>( set ) );
        }

        return gym;
    }
}
