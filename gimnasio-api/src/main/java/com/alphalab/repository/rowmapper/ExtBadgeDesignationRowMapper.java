package com.alphalab.repository.rowmapper;

import com.alphalab.domain.ExtBadgeDesignation;
import com.alphalab.domain.enumeration.ExtBadgeDesignationStatus;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link ExtBadgeDesignation}, with proper type conversions.
 */
@Service
public class ExtBadgeDesignationRowMapper implements BiFunction<Row, String, ExtBadgeDesignation> {

    private final ColumnConverter converter;

    public ExtBadgeDesignationRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link ExtBadgeDesignation} stored in the database.
     */
    @Override
    public ExtBadgeDesignation apply(Row row, String prefix) {
        ExtBadgeDesignation entity = new ExtBadgeDesignation();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setStatus(converter.fromRow(row, prefix + "_status", ExtBadgeDesignationStatus.class));
        entity.setBadgeId(converter.fromRow(row, prefix + "_badge_id", Long.class));
        entity.setUserId(converter.fromRow(row, prefix + "_user_id", String.class));
        entity.setCreatedDate(converter.fromRow(row, prefix + "_created_date", Instant.class));
        entity.setLastModifiedDate(converter.fromRow(row, prefix + "_last_modified_date", Instant.class));
        entity.setCreatedBy(converter.fromRow(row, prefix + "_created_by", String.class));
        entity.setLastModifiedBy(converter.fromRow(row, prefix + "_last_modified_by", String.class));
        return entity;
    }
}
