package com.alphalab.repository.rowmapper;

import com.alphalab.domain.RDV;
import com.alphalab.domain.enumeration.RDVStatus;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link RDV}, with proper type conversions.
 */
@Service
public class RDVRowMapper implements BiFunction<Row, String, RDV> {

    private final ColumnConverter converter;

    public RDVRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link RDV} stored in the database.
     */
    @Override
    public RDV apply(Row row, String prefix) {
        RDV entity = new RDV();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setFromDate(converter.fromRow(row, prefix + "_from_date", Instant.class));
        entity.setToDate(converter.fromRow(row, prefix + "_to_date", Instant.class));
        entity.setEnabled(converter.fromRow(row, prefix + "_enabled", Boolean.class));
        entity.setStatus(converter.fromRow(row, prefix + "_status", RDVStatus.class));
        entity.setCalendarId(converter.fromRow(row, prefix + "_calendar_id", Long.class));
        entity.setRelBadgePackId(converter.fromRow(row, prefix + "_rel_badge_pack_id", Long.class));

        entity.setCreatedDate(converter.fromRow(row, prefix + "_created_date", Instant.class));
        entity.setLastModifiedDate(converter.fromRow(row, prefix + "_last_modified_date", Instant.class));
        entity.setCreatedBy(converter.fromRow(row, prefix + "_created_by", String.class));
        entity.setLastModifiedBy(converter.fromRow(row, prefix + "_last_modified_by", String.class));
        return entity;
    }
}
