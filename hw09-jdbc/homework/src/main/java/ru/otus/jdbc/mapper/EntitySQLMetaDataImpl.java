package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final String selectAllSql;
    private final String selectByIdSql;
    private final String insertSql;
    private final String updateSql;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {

        selectAllSql = "select " + splitingFields(entityClassMetaData.getAllFields(), ", ") + " from " + entityClassMetaData.getName().toString();

        selectByIdSql = "select " + splitingFields(entityClassMetaData.getAllFields(), ", ") +
                " from " + entityClassMetaData.getName() +
                " where " + entityClassMetaData.getIdField().getName() + " = ?";

        insertSql = "insert into " + entityClassMetaData.getName()
                + "( " + splitingFields(entityClassMetaData.getFieldsWithoutId(), ", ") +
                ") values (" + Stream.generate(() -> "?")
                .limit(entityClassMetaData.getFieldsWithoutId().size())
                .collect(Collectors.joining(", ")) + ")";
        updateSql = "update " + entityClassMetaData.getName().toString() +
                " set " + splitingFields(entityClassMetaData.getFieldsWithoutId(), " = ?, ") + " = ? " +
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

    private String splitingFields(List<Field> listFields, String delimiter) {
        List<String> listNamesAllFields = new ArrayList<>();
        listFields.stream().forEach(a -> listNamesAllFields.add(a.getName()));
        return listNamesAllFields.stream().collect(Collectors.joining(delimiter));
    }
}
