package com.alphalab.repository.rowmapper;

import com.alphalab.domain.Calendar;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.time.LocalTime;
import java.util.List;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Calendar}, with proper type conversions.
 */
@Service
public class CalendarRowMapper implements BiFunction<Row, String, Calendar> {

    private final ColumnConverter converter;

    public CalendarRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Calendar} stored in the database.
     */
    @Override
    public Calendar apply(Row row, String prefix) {
        Calendar entity = new Calendar();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setStartHour(converter.fromRow(row, prefix + "_start_hour", LocalTime.class));
        entity.setEndHour(converter.fromRow(row, prefix + "_end_hour", LocalTime.class));
        entity.setUnit(converter.fromRow(row, prefix + "_unit", Integer.class));
        entity.setOffDays(converter.fromRow(row, prefix + "_off_days", List.class));
        entity.setEnabled(converter.fromRow(row, prefix + "_enabled", Boolean.class));
        entity.setGymId(converter.fromRow(row, prefix + "_gym_id", Long.class));
        entity.setCreatedDate(converter.fromRow(row, prefix + "_created_date", Instant.class));
        entity.setLastModifiedDate(converter.fromRow(row, prefix + "_last_modified_date", Instant.class));
        entity.setCreatedBy(converter.fromRow(row, prefix + "_created_by", String.class));
        entity.setLastModifiedBy(converter.fromRow(row, prefix + "_last_modified_by", String.class));
        return entity;
    }
}
