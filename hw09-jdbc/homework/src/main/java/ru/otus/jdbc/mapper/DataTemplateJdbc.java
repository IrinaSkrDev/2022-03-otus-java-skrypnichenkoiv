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
    EntityClassMetaData entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {

        return (Optional<T>) dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), Collections.singletonList(id),
                rs -> {
                    try {
                        if (rs.next()) {
                            var newObject =  entityClassMetaData.getConstructor().newInstance();
                            newObject = rs.findColumn()
                            return newObject;
                        }
                        return null;
                    } catch (SQLException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                        throw new DataTemplateException(e);
                    }
                });
    }

    @Override
    public List<T> findAll(Connection connection) {
        return (List<T>) dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), rs -> {
            try {
                return rs.next();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return Collections.emptyList();
        }).orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T client) {

        try {
            List<Object> value = new ArrayList<>();
            entityClassMetaData.getFieldsWithoutId().stream().forEach(a -> {
                Field nameF = (Field) a;
                nameF.setAccessible(true);
                try {
                    value.add(nameF.get(client).toString());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            });
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    value);
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }

    @Override
    public void update(Connection connection, T client) {

        try {


            dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), List.of(client));
        } catch (Exception e) {
            throw new DataTemplateException(e);
        }
    }
}
