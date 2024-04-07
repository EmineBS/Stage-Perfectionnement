package com.alphalab.repository.rowmapper;

import com.alphalab.domain.OffrePack;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link OffrePack}, with proper type conversions.
 */
@Service
public class OffrePackRowMapper implements BiFunction<Row, String, OffrePack> {

    private final ColumnConverter converter;

    public OffrePackRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link OffrePack} stored in the database.
     */
    @Override
    public OffrePack apply(Row row, String prefix) {
        OffrePack entity = new OffrePack();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setWorkoutSessions(converter.fromRow(row, prefix + "_workout_sessions", Integer.class));
        entity.setPrice(converter.fromRow(row, prefix + "_price", Double.class));
        entity.setAutoConfirm(converter.fromRow(row, prefix + "_auto_confirm", Boolean.class));
        entity.setRdvEnabled(converter.fromRow(row, prefix + "_rdv_enabled", Boolean.class));
        entity.setGymId(converter.fromRow(row, prefix + "_gym_id", Long.class));
        entity.setCreatedDate(converter.fromRow(row, prefix + "_created_date", Instant.class));
        entity.setLastModifiedDate(converter.fromRow(row, prefix + "_last_modified_date", Instant.class));
        entity.setCreatedBy(converter.fromRow(row, prefix + "_created_by", String.class));
        entity.setLastModifiedBy(converter.fromRow(row, prefix + "_last_modified_by", String.class));
        return entity;
    }
}
