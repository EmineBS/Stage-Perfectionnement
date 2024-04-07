package com.alphalab.service.mapper;

import com.alphalab.domain.RelUserTournament;
import com.alphalab.service.dto.RelUserTournamentDTO;
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
public class RelUserTournamentMapperImpl implements RelUserTournamentMapper {

    @Override
    public RelUserTournament toEntity(RelUserTournamentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        RelUserTournament relUserTournament = new RelUserTournament();

        relUserTournament.setCreatedBy( dto.getCreatedBy() );
        relUserTournament.setCreatedDate( dto.getCreatedDate() );
        relUserTournament.setLastModifiedBy( dto.getLastModifiedBy() );
        relUserTournament.setLastModifiedDate( dto.getLastModifiedDate() );
        relUserTournament.setId( dto.getId() );
        relUserTournament.setTournamentId( dto.getTournamentId() );
        relUserTournament.setUserId( dto.getUserId() );

        return relUserTournament;
    }

    @Override
    public RelUserTournamentDTO toDto(RelUserTournament entity) {
        if ( entity == null ) {
            return null;
        }

        RelUserTournamentDTO relUserTournamentDTO = new RelUserTournamentDTO();

        relUserTournamentDTO.setCreatedBy( entity.getCreatedBy() );
        relUserTournamentDTO.setCreatedDate( entity.getCreatedDate() );
        relUserTournamentDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        relUserTournamentDTO.setLastModifiedDate( entity.getLastModifiedDate() );
        relUserTournamentDTO.setId( entity.getId() );
        relUserTournamentDTO.setTournamentId( entity.getTournamentId() );
        relUserTournamentDTO.setUserId( entity.getUserId() );

        return relUserTournamentDTO;
    }

    @Override
    public List<RelUserTournament> toEntity(List<RelUserTournamentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<RelUserTournament> list = new ArrayList<RelUserTournament>( dtoList.size() );
        for ( RelUserTournamentDTO relUserTournamentDTO : dtoList ) {
            list.add( toEntity( relUserTournamentDTO ) );
        }

        return list;
    }

    @Override
    public List<RelUserTournamentDTO> toDto(List<RelUserTournament> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RelUserTournamentDTO> list = new ArrayList<RelUserTournamentDTO>( entityList.size() );
        for ( RelUserTournament relUserTournament : entityList ) {
            list.add( toDto( relUserTournament ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(RelUserTournament entity, RelUserTournamentDTO dto) {
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
        if ( dto.getTournamentId() != null ) {
            entity.setTournamentId( dto.getTournamentId() );
        }
        if ( dto.getUserId() != null ) {
            entity.setUserId( dto.getUserId() );
        }
    }
}
