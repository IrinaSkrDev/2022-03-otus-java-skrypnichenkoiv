package ru.otus.jdbc.mapper;

import ru.otus.crm.model.annotation.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EntityClassMetaDataImpl implements EntityClassMetaData {
    final private Class<?> clazz;
    private final List<Field> fieldsWithoutId;
    private final List<Field> allFields;
    private final Field IdField;

    public EntityClassMetaDataImpl(Class<?> clazz) {
        this.clazz =clazz;
        // не понимаю, что делаю не так. но getDeclaredFields не вытаскивает ни одного поля. а у класса в Reflectiondata =null
        Field[] allField = this.clazz.getClass().getDeclaredFields();
        System.out.println(allField[0]);
        this.allFields = Arrays.stream(this.clazz.getClass().getDeclaredFields()).collect(Collectors.toList());
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
        return this.clazz.getName();
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
