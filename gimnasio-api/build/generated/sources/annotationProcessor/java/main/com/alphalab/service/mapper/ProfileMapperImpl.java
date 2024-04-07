package com.alphalab.service.mapper;

import com.alphalab.domain.Profile;
import com.alphalab.service.dto.ProfileDTO;
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
public class ProfileMapperImpl implements ProfileMapper {

    @Override
    public Profile toEntity(ProfileDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Profile profile = new Profile();

        profile.setCreatedBy( dto.getCreatedBy() );
        profile.setCreatedDate( dto.getCreatedDate() );
        profile.setLastModifiedBy( dto.getLastModifiedBy() );
        profile.setLastModifiedDate( dto.getLastModifiedDate() );
        profile.setId( dto.getId() );
        profile.setName( dto.getName() );
        profile.setLastName( dto.getLastName() );
        profile.setEmail( dto.getEmail() );
        profile.setPhoneNumber( dto.getPhoneNumber() );
        profile.setBadgeId( dto.getBadgeId() );

        return profile;
    }

    @Override
    public ProfileDTO toDto(Profile entity) {
        if ( entity == null ) {
            return null;
        }

        ProfileDTO profileDTO = new ProfileDTO();

        profileDTO.setCreatedBy( entity.getCreatedBy() );
        profileDTO.setCreatedDate( entity.getCreatedDate() );
        profileDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        profileDTO.setLastModifiedDate( entity.getLastModifiedDate() );
        profileDTO.setId( entity.getId() );
        profileDTO.setName( entity.getName() );
        profileDTO.setLastName( entity.getLastName() );
        profileDTO.setPhoneNumber( entity.getPhoneNumber() );
        profileDTO.setEmail( entity.getEmail() );
        profileDTO.setBadgeId( entity.getBadgeId() );

        return profileDTO;
    }

    @Override
    public List<Profile> toEntity(List<ProfileDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Profile> list = new ArrayList<Profile>( dtoList.size() );
        for ( ProfileDTO profileDTO : dtoList ) {
            list.add( toEntity( profileDTO ) );
        }

        return list;
    }

    @Override
    public List<ProfileDTO> toDto(List<Profile> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<ProfileDTO> list = new ArrayList<ProfileDTO>( entityList.size() );
        for ( Profile profile : entityList ) {
            list.add( toDto( profile ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Profile entity, ProfileDTO dto) {
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
        if ( dto.getLastName() != null ) {
            entity.setLastName( dto.getLastName() );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( dto.getEmail() );
        }
        if ( dto.getPhoneNumber() != null ) {
            entity.setPhoneNumber( dto.getPhoneNumber() );
        }
        if ( dto.getBadgeId() != null ) {
            entity.setBadgeId( dto.getBadgeId() );
        }
    }
}
