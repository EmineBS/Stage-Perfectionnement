package com.alphalab.repository.rowmapper;

import com.alphalab.domain.Gym;
import com.alphalab.domain.enumeration.BadgeStatus;
import com.alphalab.domain.enumeration.GymStatus;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Gym}, with proper type conversions.
 */
@Service
public class GymRowMapper implements BiFunction<Row, String, Gym> {

    private final ColumnConverter converter;

    public GymRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Gym} stored in the database.
     */
    @Override
    public Gym apply(Row row, String prefix) {
        Gym entity = new Gym();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setName(converter.fromRow(row, prefix + "_name", String.class));
        entity.setDescription(converter.fromRow(row, prefix + "_description", String.class));
        entity.setLocation(converter.fromRow(row, prefix + "_location", String.class));
        entity.setPhoneNumber(converter.fromRow(row, prefix + "_phone_number", String.class));
        entity.setEmail(converter.fromRow(row, prefix + "_email", String.class));
        entity.setRegistrationNumber(converter.fromRow(row, prefix + "_registration_number", String.class));
        entity.setBadgeAmount(converter.fromRow(row, prefix + "_badge_amount", Integer.class));
        entity.setStatus(converter.fromRow(row, prefix + "_status", GymStatus.class));
        entity.setCreatedDate(converter.fromRow(row, prefix + "_created_date", Instant.class));
        entity.setLastModifiedDate(converter.fromRow(row, prefix + "_last_modified_date", Instant.class));
        entity.setCreatedBy(converter.fromRow(row, prefix + "_created_by", String.class));
        entity.setLastModifiedBy(converter.fromRow(row, prefix + "_last_modified_by", String.class));
        return entity;
    }
}
