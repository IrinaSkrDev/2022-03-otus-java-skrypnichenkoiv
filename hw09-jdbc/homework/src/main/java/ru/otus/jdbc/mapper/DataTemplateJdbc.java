package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {

        return (Optional<T>) dbExecutor.executeSelect(connection,entitySQLMetaData.getSelectByIdSql(), List.of(id),
                rs -> {
                    try {
                        if (rs.next()) {
                       System.out.println(rs.next());
                       return rs.next();
                        }
                        return null;
                    } catch (SQLException e) {
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
            return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(),
                    Collections.singletonList(client));
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
