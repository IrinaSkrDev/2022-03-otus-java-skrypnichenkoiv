package ru.otus.jdbc.mapper;

import ru.otus.crm.model.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl implements EntityClassMetaData {
    final private Class<?> clazz;
    private final List<Field> fields;

    public EntityClassMetaDataImpl(Object className) {
        clazz = className.getClass();
        fields = Arrays.stream(this.clazz.getClass().getFields()).filter(met -> {
                    return met.isAnnotationPresent(Id.class);
                }
        ).collect(Collectors.toList());

    }

    @Override
    public String getName() {
        return clazz.getClass().getName();
    }

    @Override
    public Constructor getConstructor() throws NoSuchMethodException {
        return clazz.getConstructor();
    }

    @Id
    @Override
    public Field getIdField() {
        return fields.stream().filter(met -> {
                    return met.isAnnotationPresent(Id.class);
                }
        ).findFirst().get();

    }

    @Override
    public List<Field> getAllFields() {
        return fields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fields.stream().filter(met -> {
                    return !met.isAnnotationPresent(Id.class);
                }
        ).toList();
    }
}
