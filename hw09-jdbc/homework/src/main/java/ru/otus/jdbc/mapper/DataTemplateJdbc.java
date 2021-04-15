package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.DataTemplateException;
import ru.otus.core.repository.executor.DbExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {
    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final EntityClassMetaData entityClassMetaData;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, EntityClassMetaData entityClassMetaData) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectByIdSql(), List.of(id), this::getObjectByResultSet);
    }

    @Override
    public List<T> findAll(Connection connection) {
        return dbExecutor.executeSelect(connection, entitySQLMetaData.getSelectAllSql(), Collections.emptyList(), this::getAllObjectsByResultSet)
                .orElseThrow(() -> new RuntimeException("Unexpected error"));
    }

    @Override
    public long insert(Connection connection, T object) {
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), getFieldValuesForInsert(object));
    }

    @Override
    public void update(Connection connection, T object) {
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(),
                getFieldValuesForUpdate(object));
    }

    private List<Object> getFieldValuesForInsert(T object) {
        List<Object> fieldValues = new ArrayList<>();
        for (Field field : entityClassMetaData.getFieldsWithoutId()) {
            try {
                fieldValues.add(field.get(object));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return fieldValues;
    }

    private List<Object> getFieldValuesForUpdate(T object) {
        List<Object> fieldValues = getFieldValuesForInsert(object);
        try {
            fieldValues.add(entityClassMetaData.getIdField().get(object));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return fieldValues;
    }


    private T createObjectInstance(ResultSet resultSet) throws SQLException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object object = entityClassMetaData.getConstructor().newInstance();
        List<Field> objectFields = entityClassMetaData.getAllFields();
        for (Field objectField : objectFields) {
            var value = resultSet.getObject(objectField.getName());
            objectField.set(object, value);
        }
        return (T) object;
    }

    private T getObjectByResultSet(ResultSet resultSet) {
        try {
            if (resultSet.next()) {
                return createObjectInstance(resultSet);
            }
            return null;
        } catch (SQLException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new DataTemplateException(e);
        }
    }

    private List<T> getAllObjectsByResultSet(ResultSet resultSet) {
        List<T> objectList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                objectList.add(createObjectInstance(resultSet));
            }
        } catch (SQLException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return  objectList;
    }

}
