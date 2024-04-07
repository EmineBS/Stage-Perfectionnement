package com.alphalab.service.mapper;

import com.alphalab.domain.Tournament;
import com.alphalab.domain.Game;
import com.alphalab.service.dto.TournamentDTO;
import com.alphalab.service.dto.GameDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface TournamentMapper extends EntityMapper<TournamentDTO, Tournament> {
    @Mapping(source = "name", target = "name")
    TournamentDTO toDto(Tournament tournament);
}
