package com.alphalab.repository.rowmapper;

import com.alphalab.domain.Badge;
import com.alphalab.domain.enumeration.BadgeStatus;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Badge}, with proper type conversions.
 */
@Service
public class BadgeRowMapper implements BiFunction<Row, String, Badge> {

    private final ColumnConverter converter;

    public BadgeRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Badge} stored in the database.
     */
    @Override
    public Badge apply(Row row, String prefix) {
        Badge entity = new Badge();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setUid(converter.fromRow(row, prefix + "_uid", String.class));
        entity.setStatus(converter.fromRow(row, prefix + "_status", BadgeStatus.class));
        entity.setEnabled(converter.fromRow(row, prefix + "_enabled", Boolean.class));
        entity.setGymId(converter.fromRow(row, prefix + "_gym_id", Long.class));
        entity.setCreatedDate(converter.fromRow(row, prefix + "_created_date", Instant.class));
        entity.setLastModifiedDate(converter.fromRow(row, prefix + "_last_modified_date", Instant.class));
        entity.setCreatedBy(converter.fromRow(row, prefix + "_created_by", String.class));
        entity.setLastModifiedBy(converter.fromRow(row, prefix + "_last_modified_by", String.class));
        return entity;
    }
}
