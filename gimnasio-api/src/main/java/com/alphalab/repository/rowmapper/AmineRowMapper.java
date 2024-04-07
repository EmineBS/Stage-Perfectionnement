package com.alphalab.repository.rowmapper;

import com.alphalab.domain.Amine;
import com.alphalab.domain.enumeration.BadgeStatus;
import io.r2dbc.spi.Row;
import java.time.Instant;
import java.util.function.BiFunction;
import org.springframework.stereotype.Service;

/**
 * Converter between {@link Row} to {@link Amine}, with proper type conversions.
 */
@Service
public class AmineRowMapper implements BiFunction<Row, String, Amine> {

    private final ColumnConverter converter;

    public AmineRowMapper(ColumnConverter converter) {
        this.converter = converter;
    }

    /**
     * Take a {@link Row} and a column prefix, and extract all the fields.
     * @return the {@link Amine} stored in the database.
     */
    @Override
    public Amine apply(Row row, String prefix) {
        Amine entity = new Amine();
        entity.setId(converter.fromRow(row, prefix + "_id", Long.class));
        entity.setMyName(converter.fromRow(row, prefix + "_myname", String.class));
        entity.setFamilyName(converter.fromRow(row, prefix + "_familyname", String.class));
        entity.setAge(converter.fromRow(row, prefix + "_age", Integer.class));
        return entity;
    }
}
