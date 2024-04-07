package com.alphalab.repository.rowmapper;

import com.alphalab.domain.Game;
import com.alphalab.domain.enumeration.BadgeStatus;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

@Service
public class GameRowMapper implements BiFunction<Row, String, Game> {

    private final ColumnConverter converter;

    public GameRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Amine} stored in the database.
     */
    @Override
    public Game apply(Row row, String prefix) {
        Game entity = new Game();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setGameName(converter.fromRow(row, prefix + "_gamename", String.class));
        entity.setStyle(converter.fromRow(row, prefix + "_style", String.class));
        return entity;
    }
}