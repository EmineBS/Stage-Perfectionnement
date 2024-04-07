package com.alphalab.repository.rowmapper;

import com.alphalab.domain.RelUserTournament;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

@Service
public class RelUserTournamentRowMapper implements BiFunction<Row, String, RelUserTournament>{
    private final ColumnConverter converter;

    public RelUserTournamentRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    @Override
    public RelUserTournament apply(Row row, String prefix) {
        RelUserTournament entity = new RelUserTournament();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setUserId(converter.fromRow(row, prefix + "_user_id", String.class));
        entity.setTournamentId(converter.fromRow(row, prefix + "_tournament_id", Long.class));
        entity.setCreatedDate(converter.fromRow(row, prefix + "_created_date", Instant.class));
        entity.setLastModifiedDate(converter.fromRow(row, prefix + "_last_modified_date", Instant.class));
        entity.setCreatedBy(converter.fromRow(row, prefix + "_created_by", String.class));
        entity.setLastModifiedBy(converter.fromRow(row, prefix + "_last_modified_by", String.class));
        return entity;
    }
}
