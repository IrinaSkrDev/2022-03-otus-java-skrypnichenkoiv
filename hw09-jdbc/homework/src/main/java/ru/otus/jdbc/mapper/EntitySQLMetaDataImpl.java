package ru.otus.jdbc.mapper;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final String selectAllSql;
    private final String selectByIdSql;
    private final String insertSql;
    private final String updateSql;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        String listOfFieldSplitingByComma = entityClassMetaData.getAllFields().stream().map(field -> new String(field.toString())).collect(Collectors.joining(", ")).toString();
        selectAllSql = "select " + listOfFieldSplitingByComma + " from " + entityClassMetaData.getName();
        selectByIdSql = "select " + listOfFieldSplitingByComma +
                " from " + entityClassMetaData.getName() +
                " where " + entityClassMetaData.getIdField().getName() + " = ?";
        insertSql = "insert into " + entityClassMetaData.getName() + "( " + entityClassMetaData.getFieldsWithoutId().stream().map(field -> new String(field.toString())).collect(Collectors.joining(", ")) +
                  ") values (" + Stream.generate(() -> "?")
                .limit(entityClassMetaData.getFieldsWithoutId().size())
                .collect(Collectors.joining(", ")) + ")";
        updateSql = "update " + entityClassMetaData.getName() +
                      " set " + entityClassMetaData.getFieldsWithoutId().stream().map(field -> new String(field.toString())).collect(Collectors.joining(" = ?, ")) +
                    " where " + entityClassMetaData.getIdField().getName() + " = ?";
    }

    @Override
    public String getSelectAllSql() {
        return selectAllSql;
    }

    @Override
    public String getSelectByIdSql() {
        return selectByIdSql;
    }

    @Override
    public String getInsertSql() {
        return insertSql;
    }

    @Override
    public String getUpdateSql() {
        return updateSql;
    }
}
