package com.alphalab.service.mapper;

import com.alphalab.domain.Tournament;
import com.alphalab.service.dto.TournamentDTO;
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
public class TournamentMapperImpl implements TournamentMapper {

    @Override
    public Tournament toEntity(TournamentDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Tournament tournament = new Tournament();

        tournament.setCreatedBy( dto.getCreatedBy() );
        tournament.setCreatedDate( dto.getCreatedDate() );
        tournament.setLastModifiedBy( dto.getLastModifiedBy() );
        tournament.setLastModifiedDate( dto.getLastModifiedDate() );
        tournament.setId( dto.getId() );
        tournament.setName( dto.getName() );
        tournament.setRegistration( dto.getRegistration() );
        tournament.setStarttimestamp( dto.getStarttimestamp() );
        tournament.setMinplayers( dto.getMinplayers() );
        tournament.setMaxplayers( dto.getMaxplayers() );
        tournament.setGameId( dto.getGameId() );

        return tournament;
    }

    @Override
    public List<Tournament> toEntity(List<TournamentDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Tournament> list = new ArrayList<Tournament>( dtoList.size() );
        for ( TournamentDTO tournamentDTO : dtoList ) {
            list.add( toEntity( tournamentDTO ) );
        }

        return list;
    }

    @Override
    public List<TournamentDTO> toDto(List<Tournament> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<TournamentDTO> list = new ArrayList<TournamentDTO>( entityList.size() );
        for ( Tournament tournament : entityList ) {
            list.add( toDto( tournament ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Tournament entity, TournamentDTO dto) {
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
        if ( dto.getRegistration() != null ) {
            entity.setRegistration( dto.getRegistration() );
        }
        if ( dto.getStarttimestamp() != null ) {
            entity.setStarttimestamp( dto.getStarttimestamp() );
        }
        if ( dto.getMinplayers() != null ) {
            entity.setMinplayers( dto.getMinplayers() );
        }
        if ( dto.getMaxplayers() != null ) {
            entity.setMaxplayers( dto.getMaxplayers() );
        }
        if ( dto.getGameId() != null ) {
            entity.setGameId( dto.getGameId() );
        }
    }

    @Override
    public TournamentDTO toDto(Tournament tournament) {
        if ( tournament == null ) {
            return null;
        }

        TournamentDTO tournamentDTO = new TournamentDTO();

        tournamentDTO.setName( tournament.getName() );
        tournamentDTO.setCreatedBy( tournament.getCreatedBy() );
        tournamentDTO.setCreatedDate( tournament.getCreatedDate() );
        tournamentDTO.setLastModifiedBy( tournament.getLastModifiedBy() );
        tournamentDTO.setLastModifiedDate( tournament.getLastModifiedDate() );
        tournamentDTO.setId( tournament.getId() );
        tournamentDTO.setRegistration( tournament.getRegistration() );
        tournamentDTO.setStarttimestamp( tournament.getStarttimestamp() );
        tournamentDTO.setMinplayers( tournament.getMinplayers() );
        tournamentDTO.setMaxplayers( tournament.getMaxplayers() );
        tournamentDTO.setGameId( tournament.getGameId() );

        return tournamentDTO;
    }
}
