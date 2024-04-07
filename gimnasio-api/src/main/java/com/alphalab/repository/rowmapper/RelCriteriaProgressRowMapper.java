package com.alphalab.repository.rowmapper;

import com.alphalab.domain.RelCriteriaProgress;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link RelCriteriaProgress}, with proper type conversions.
 */
@Service
public class RelCriteriaProgressRowMapper implements BiFunction<Row, String, RelCriteriaProgress> {

    private final ColumnConverter converter;

    public RelCriteriaProgressRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link RelCriteriaProgress} stored in the database.
     */
    @Override
    public RelCriteriaProgress apply(Row row, String prefix) {
        RelCriteriaProgress entity = new RelCriteriaProgress();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setValue(converter.fromRow(row, prefix + "_value", Double.class));
        entity.setCriteriaId(converter.fromRow(row, prefix + "_criteria_id", Long.class));
        entity.setProgressId(converter.fromRow(row, prefix + "_progress_id", Long.class));

        entity.setCreatedDate(converter.fromRow(row, prefix + "_created_date", Instant.class));
        entity.setLastModifiedDate(converter.fromRow(row, prefix + "_last_modified_date", Instant.class));
        entity.setCreatedBy(converter.fromRow(row, prefix + "_created_by", String.class));
        entity.setLastModifiedBy(converter.fromRow(row, prefix + "_last_modified_by", String.class));
        return entity;
    }
}
