package com.alphalab.repository.rowmapper;

import com.alphalab.domain.Progress;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Progress}, with proper type conversions.
 */
@Service
public class ProgressRowMapper implements BiFunction<Row, String, Progress> {

    private final ColumnConverter converter;

    public ProgressRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Progress} stored in the database.
     */
    @Override
    public Progress apply(Row row, String prefix) {
        Progress entity = new Progress();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setBadgeId(converter.fromRow(row, prefix + "_badge_id", Long.class));

        entity.setCreatedDate(converter.fromRow(row, prefix + "_created_date", Instant.class));
        entity.setLastModifiedDate(converter.fromRow(row, prefix + "_last_modified_date", Instant.class));
        entity.setCreatedBy(converter.fromRow(row, prefix + "_created_by", String.class));
        entity.setLastModifiedBy(converter.fromRow(row, prefix + "_last_modified_by", String.class));
        return entity;
    }
}
