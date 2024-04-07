package com.alphalab.repository.rowmapper;

import com.alphalab.domain.CheckIn;
import com.alphalab.domain.enumeration.CheckinStatus;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link CheckIn}, with proper type conversions.
 */
@Service
public class CheckinRowMapper implements BiFunction<Row, String, CheckIn> {

    private final ColumnConverter converter;

    public CheckinRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link CheckIn} stored in the database.
     */
    @Override
    public CheckIn apply(Row row, String prefix) {
        CheckIn entity = new CheckIn();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setStatus(converter.fromRow(row, prefix + "_status", CheckinStatus.class));
        entity.setRelBadgePackId(converter.fromRow(row, prefix + "_rel_badge_pack_id", Long.class));
        entity.setUserId(converter.fromRow(row, prefix + "_user_id", String.class));
        entity.setCreatedDate(converter.fromRow(row, prefix + "_created_date", Instant.class));
        entity.setLastModifiedDate(converter.fromRow(row, prefix + "_last_modified_date", Instant.class));
        entity.setCreatedBy(converter.fromRow(row, prefix + "_created_by", String.class));
        entity.setLastModifiedBy(converter.fromRow(row, prefix + "_last_modified_by", String.class));
        return entity;
    }
}
