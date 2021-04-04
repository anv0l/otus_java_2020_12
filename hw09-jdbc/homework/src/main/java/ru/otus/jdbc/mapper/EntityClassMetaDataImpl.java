package ru.otus.jdbc.mapper;

import ru.otus.crm.model.Id;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData {
    private final String className;
    private final List<Field> allFields;
    private final Field fieldId;
    private final List<Field> fieldsWithoutId;
    private final Constructor<T> constructor;

    private static final Class<? extends Annotation> PRIMARY_KEY_ID = Id.class;

    public EntityClassMetaDataImpl(Class<T> clazz)  {
        Field[] declaredFields = clazz.getDeclaredFields();

        this.className = clazz.getSimpleName();
        this.allFields = Arrays.asList(declaredFields);
        this.fieldId = getFieldId(declaredFields);
        this.fieldsWithoutId = getFieldsWithoutId(declaredFields);
        this.constructor = getConstructor(clazz);
    }

    private Field getFieldId(Field[] declaredFields) {
        Field found = null;
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(PRIMARY_KEY_ID)) {
                found = declaredField;
                found.setAccessible(true);
                break;
            }
        }
        return found;
    }

    private List<Field> getFieldsWithoutId(Field[] declaredFields) {
        List<Field> list = new ArrayList<>();
        for (Field field : declaredFields) {
            if (!field.isAnnotationPresent(PRIMARY_KEY_ID)) {
                field.setAccessible(true);
                list.add(field);
            }
        }
        return list;
    }

    private Constructor<T> getConstructor(Class<T> clazz) {
        Constructor<T> constructor = null;
        try {
            constructor = clazz.getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return constructor;
    }

    @Override
    public String getName() {
        return this.className;
    }

    @Override
    public <T> Constructor<T> getConstructor() {
        return (Constructor<T>) this.constructor;
    }

    @Override
    public Field getIdField() {
        return this.fieldId;
    }

    @Override
    public List<Field> getAllFields() {
        return this.allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return this.fieldsWithoutId;
    }
}
