package com.alphalab.repository.rowmapper;

import com.alphalab.domain.Tournament;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

@Service
public class TournamentRowMapper implements BiFunction<Row, String, Tournament> {

    private final ColumnConverter converter;

    public TournamentRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    @Override
    public Tournament apply(Row row, String prefix) {
        Tournament entity = new Tournament();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setRegistration(converter.fromRow(row, prefix + "_registration", Boolean.class));
        entity.setStarttimestamp(converter.fromRow(row, prefix + "_starttimestamp", Number.class));
        entity.setMinplayers(converter.fromRow(row, prefix + "_minplayers", Number.class));
        entity.setMaxplayers(converter.fromRow(row, prefix + "_maxplayers", Number.class));
        entity.setGameId(converter.fromRow(row, prefix + "_gameid", Long.class));
        return entity;
    }

}
