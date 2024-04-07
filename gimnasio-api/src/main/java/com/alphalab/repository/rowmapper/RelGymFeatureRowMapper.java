package com.alphalab.repository.rowmapper;

import com.alphalab.domain.RelGymFeature;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link RelGymFeature}, with proper type conversions.
 */
@Service
public class RelGymFeatureRowMapper implements BiFunction<Row, String, RelGymFeature> {

    private final ColumnConverter converter;

    public RelGymFeatureRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link RelGymFeature} stored in the database.
     */
    @Override
    public RelGymFeature apply(Row row, String prefix) {
        RelGymFeature entity = new RelGymFeature();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setGymId(converter.fromRow(row, prefix + "_gym_id", Long.class));
        entity.setFeatureId(converter.fromRow(row, prefix + "_feature_id", Long.class));
        entity.setCreatedDate(converter.fromRow(row, prefix + "_created_date", Instant.class));
        entity.setLastModifiedDate(converter.fromRow(row, prefix + "_last_modified_date", Instant.class));
        entity.setCreatedBy(converter.fromRow(row, prefix + "_created_by", String.class));
        entity.setLastModifiedBy(converter.fromRow(row, prefix + "_last_modified_by", String.class));
        return entity;
    }
}
