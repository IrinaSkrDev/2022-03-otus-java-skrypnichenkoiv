package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData<T> entityClassMetaData;


    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData<T> entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), Collections.singletonList(id), rs -> {
            try {
                if (rs.next()) {
                    var newObjectToReturn = entityClassMetaData.getConstructor().newInstance();
                    List<Field> allField = entityClassMetaData.getAllFields();
                    for (Field field : allField) {
                        field.setAccessible(true);
                        field.set(newObjectToReturn, rs.getObject(field.getName()));
                    }
                    return newObjectToReturn;
                }
                return null;
            } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new DataTemplateException(e);
            }
        });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            var objectList = new ArrayList<T>();
            try {

                while (rs.next()) {
                    var newObjectToReturn = entityClassMetaData.getConstructor().newInstance();
                    List<Field> allField = entityClassMetaData.getAllFields();
                    for (Field field : allField) {
                        field.setAccessible(true);
                        field.set(newObjectToReturn, rs.getObject(field.getName()));
                    }
                    objectList.add(newObjectToReturn);
                }
                return objectList;
            } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new DataTemplateException(e);
            }
        }).orElseThrow(() -> new DataTemplateException(new Exception()));
    }

    @Override
    public long insert(Connection connection, T object) {

        try {
            List<Object> value = new ArrayList<>();
            entityClassMetaData.getFieldsWithoutId().stream().forEach(a -> {
                Field nameF = (Field) a;
                nameF.setAccessible(true);
                try {
                    var valeInField = nameF.get(object);
                    value.add(valeInField);
                } catch (IllegalAccessException e) {
                    throw new DataTemplateException(e);
                }
            });
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), value);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T object) {

        try {
            List<Object> value = new ArrayList<>();
            entityClassMetaData.getFieldsWithoutId().stream().forEach(a -> {
                        Field nameF = (Field) a;
                        nameF.setAccessible(true);
                        try {
                            var valeInField = nameF.get(object);
                            value.add(valeInField);
                        } catch (IllegalAccessException e) {
                            throw new DataTemplateException(e);
                        }
                    }
            );
            value.add(entityClassMetaData.getIdField().get(object));
            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), value);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
