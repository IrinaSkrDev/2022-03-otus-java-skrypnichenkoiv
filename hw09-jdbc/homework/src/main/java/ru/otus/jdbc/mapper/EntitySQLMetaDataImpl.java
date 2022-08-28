package ru.otus.jdbc.mapper;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData entityClassMetaData;
    private final String listOfFieldSplitingByComma;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
        listOfFieldSplitingByComma = entityClassMetaData.getAllFields().stream().map(field -> new String(field.toString())).collect(Collectors.joining(", ")).toString();
    }

    @Override
    public String getSelectAllSql() {
        return "select " + listOfFieldSplitingByComma +
                " from " + entityClassMetaData.getName();
    }

    @Override
    public String getSelectByIdSql() {
        return "select " + listOfFieldSplitingByComma +
                " from " + entityClassMetaData.getName() +
                " where " + entityClassMetaData.getIdField().getName() + " = ?"
                ;
    }

    @Override
    public String getInsertSql() {
        return "insert into " + entityClassMetaData.getName() + "( " + entityClassMetaData.getFieldsWithoutId().stream().map(field -> new String(field.toString())).collect(Collectors.joining(", ")) +
                ") values (" + Stream.generate(() -> "?")
                .limit(entityClassMetaData.getFieldsWithoutId().size())
                .collect(Collectors.joining(", ")) + ")";
    }

    @Override
    public String getUpdateSql() {
        return "update " + entityClassMetaData.getName() +
                " set " + entityClassMetaData.getFieldsWithoutId().stream().map(field -> new String(field.toString())).collect(Collectors.joining(" = ?, ")) +
                " where " + entityClassMetaData.getIdField().getName() + " = ?";
    }
}
