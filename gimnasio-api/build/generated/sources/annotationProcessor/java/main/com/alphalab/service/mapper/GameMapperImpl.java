package com.alphalab.service.mapper;

import com.alphalab.domain.Game;
import com.alphalab.service.dto.GameDTO;
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
public class GameMapperImpl implements GameMapper {

    @Override
    public Game toEntity(GameDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Game game = new Game();

        game.setCreatedBy( dto.getCreatedBy() );
        game.setCreatedDate( dto.getCreatedDate() );
        game.setLastModifiedBy( dto.getLastModifiedBy() );
        game.setLastModifiedDate( dto.getLastModifiedDate() );
        game.setId( dto.getId() );
        game.setGameName( dto.getGameName() );
        game.setStyle( dto.getStyle() );

        return game;
    }

    @Override
    public GameDTO toDto(Game entity) {
        if ( entity == null ) {
            return null;
        }

        GameDTO gameDTO = new GameDTO();

        gameDTO.setCreatedBy( entity.getCreatedBy() );
        gameDTO.setCreatedDate( entity.getCreatedDate() );
        gameDTO.setLastModifiedBy( entity.getLastModifiedBy() );
        gameDTO.setLastModifiedDate( entity.getLastModifiedDate() );
        gameDTO.setId( entity.getId() );
        gameDTO.setGameName( entity.getGameName() );
        gameDTO.setStyle( entity.getStyle() );

        return gameDTO;
    }

    @Override
    public List<Game> toEntity(List<GameDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Game> list = new ArrayList<Game>( dtoList.size() );
        for ( GameDTO gameDTO : dtoList ) {
            list.add( toEntity( gameDTO ) );
        }

        return list;
    }

    @Override
    public List<GameDTO> toDto(List<Game> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<GameDTO> list = new ArrayList<GameDTO>( entityList.size() );
        for ( Game game : entityList ) {
            list.add( toDto( game ) );
        }

        return list;
    }

    @Override
    public void partialUpdate(Game entity, GameDTO dto) {
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
        if ( dto.getGameName() != null ) {
            entity.setGameName( dto.getGameName() );
        }
        if ( dto.getStyle() != null ) {
            entity.setStyle( dto.getStyle() );
        }
    }
}
