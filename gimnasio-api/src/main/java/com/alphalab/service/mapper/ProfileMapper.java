package com.alphalab.service.mapper;

import com.alphalab.domain.Profile;
import com.alphalab.service.dto.ProfileDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity {@link Profile} and its DTO {@link ProfileDTO}.
 */
@Mapper(componentModel = "spring")
public interface ProfileMapper extends EntityMapper<ProfileDTO, Profile> {
    /*
    @Mapping(source = "user.id", target = "userId")
    Profile toEntity(ProfileDTO gymDTO);
*/

}
