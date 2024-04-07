package com.alphalab.repository.rowmapper;

import com.alphalab.domain.RelBadgePack;
import com.alphalab.domain.enumeration.BadgePackStatus;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link RelBadgePack}, with proper type conversions.
 */
@Service
public class RelBadgePackRowMapper implements BiFunction<Row, String, RelBadgePack> {

    private final ColumnConverter converter;

    public RelBadgePackRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link RelBadgePack} stored in the database.
     */
    @Override
    public RelBadgePack apply(Row row, String prefix) {
        RelBadgePack entity = new RelBadgePack();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setEnabled(converter.fromRow(row, prefix + "_enabled", Boolean.class));
        entity.setStatus(converter.fromRow(row, prefix + "_status", BadgePackStatus.class));
        entity.setPackId(converter.fromRow(row, prefix + "_pack_id", Long.class));
        entity.setBadgeId(converter.fromRow(row, prefix + "_badge_id", Long.class));
        entity.setCreatedDate(converter.fromRow(row, prefix + "_created_date", Instant.class));
        entity.setLastModifiedDate(converter.fromRow(row, prefix + "_last_modified_date", Instant.class));
        entity.setCreatedBy(converter.fromRow(row, prefix + "_created_by", String.class));
        entity.setLastModifiedBy(converter.fromRow(row, prefix + "_last_modified_by", String.class));
        return entity;
    }
}
