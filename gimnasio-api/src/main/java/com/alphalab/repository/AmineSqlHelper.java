package com.alphalab.repository;

import org.springframework.data.relational.core.sql.Column;
import org.springframework.data.relational.core.sql.Expression;
import org.springframework.data.relational.core.sql.Table;

import java.util.ArrayList;
import java.util.List;

public class AmineSqlHelper {

    public static List<Expression> getColumns(Table table, String columnPrefix) {
        List<Expression> columns = new ArrayList<>();
        columns.add(Column.aliased("id", table, columnPrefix + "_id"));
        columns.add(Column.aliased("myname", table, columnPrefix + "_myname"));
        columns.add(Column.aliased("familyname", table, columnPrefix + "_familyname"));
        columns.add(Column.aliased("age", table, columnPrefix + "_age"));
        return columns;
    }
}
