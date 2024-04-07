package com.alphalab.service.mapper;

import com.alphalab.domain.RelUserTournament;
import com.alphalab.service.dto.RelUserTournamentDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RelUserTournamentMapper extends EntityMapper<RelUserTournamentDTO, RelUserTournament> {}

