package ru.otus.jdbc.mapper;

import ru.otus.crm.model.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    final private Class<?> clazz;
    private final List<Field> fieldsWithoutId;
    private final List<Field> allFields;
    private final Field IdField;

    public <T> EntityClassMetaDataImpl(Class<?> clazz) {
        this.clazz = clazz;
        Field[] allField = this.clazz.getDeclaredFields();
        System.out.println(allField[0]);
        this.allFields = Arrays.stream(this.clazz.getDeclaredFields()).collect(Collectors.toList());
        this.IdField = allFields.stream().filter(met -> {
                    return met.isAnnotationPresent(Id.class);
                }
        ).findFirst().get();
        this.fieldsWithoutId = allFields.stream().filter(met -> {
                    return !met.isAnnotationPresent(Id.class);
                }
        ).toList();
    }

    @Override
    public String getName() {

        return this.clazz.getSimpleName().toLowerCase(Locale.ROOT);
    }

    @Override
    public Constructor getConstructor() throws NoSuchMethodException {
        return clazz.getConstructor();
    }

    @Id
    @Override
    public Field getIdField() {
        return this.IdField;
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
