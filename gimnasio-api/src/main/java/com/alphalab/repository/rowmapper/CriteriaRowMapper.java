package com.alphalab.repository.rowmapper;

import com.alphalab.domain.Criteria;
import com.alphalab.domain.enumeration.CriteriaDisplay;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Criteria}, with proper type conversions.
 */
@Service
public class CriteriaRowMapper implements BiFunction<Row, String, Criteria> {

    private final ColumnConverter converter;

    public CriteriaRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Criteria} stored in the database.
     */
    @Override
    public Criteria apply(Row row, String prefix) {
        Criteria entity = new Criteria();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setEnabled(converter.fromRow(row, prefix + "_enabled", Boolean.class));
        entity.setDisplay(converter.fromRow(row, prefix + "_display", CriteriaDisplay.class));
        entity.setGymId(converter.fromRow(row, prefix + "_gym_id", Long.class));

        entity.setCreatedDate(converter.fromRow(row, prefix + "_created_date", Instant.class));
        entity.setLastModifiedDate(converter.fromRow(row, prefix + "_last_modified_date", Instant.class));
        entity.setCreatedBy(converter.fromRow(row, prefix + "_created_by", String.class));
        entity.setLastModifiedBy(converter.fromRow(row, prefix + "_last_modified_by", String.class));
        return entity;
    }
}
