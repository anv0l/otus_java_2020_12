package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData{
    private final EntityClassMetaData entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return getSelectClause();
    }

    @Override
    public String getSelectByIdSql() {
        return getSelectClause() +
                " WHERE " + entityClassMetaData.getIdField().getName() + " = ?";
    }

    @Override
    public String getInsertSql() {
        return "INSERT INTO " +
                entityClassMetaData.getName() +
                " ( " + getInsertFields() + " ) " +
                " VALUES ( " + getInsertFieldsMask() + ") ";
    }

    @Override
    public String getUpdateSql() {
        return "UPDATE " + entityClassMetaData.getName() +
                getSetClauseForUpdate() +
                " WHERE " + entityClassMetaData.getIdField().getName() + " IN ( ? )";
    }

    private String getInsertFields() {
        return entityClassMetaData.getFieldsWithoutId().stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
    }

    private String getSelectFields() {
        return entityClassMetaData.getAllFields().stream()
                .map(Field::getName)
                .collect(Collectors.joining(", "));
    }

    private String getInsertFieldsMask() {
        StringBuilder fieldMask = new StringBuilder();
        for (int i = 0; i < (long) entityClassMetaData.getFieldsWithoutId().size(); i++) {
            fieldMask.append("?, ");
        }
        if (fieldMask.length() > 2) {
            fieldMask = new StringBuilder(fieldMask.substring(0, fieldMask.length() - 2));
        }
        return fieldMask.toString();
    }

    private String getSetClauseForUpdate() {
        StringBuilder setClauseStringBuilder = new StringBuilder();
        for (Field field: entityClassMetaData.getFieldsWithoutId()) {
            setClauseStringBuilder.append(" SET ").append(field.getName()).append(" = ?, ");
        }
        String setClause = setClauseStringBuilder.toString();
        if (setClause.length() > 0) {
            setClause = setClause.substring(0, setClause.length() - 2);
        }
        return setClause;
    }

    private String getSelectClause() {
        return "SELECT " +
                getSelectFields() +
                " FROM " +
                entityClassMetaData.getName();
    }
}
