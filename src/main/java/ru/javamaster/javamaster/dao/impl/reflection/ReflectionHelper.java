package ru.javamaster.javamaster.dao.impl.reflection;

import java.lang.reflect.Field;

public class ReflectionHelper<T> {

    public void setPosition(T entity, Class<?> pc, Integer position, String positionFieldName) {
        try {
            Field field = pc.getDeclaredField(positionFieldName);
            field.set(entity, position);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public <K, T> K getId(T entity, Class<?> pc, String idFieldName) {
        K id = null;

        try {
            Field field = pc.getDeclaredField(idFieldName);
            id = (K) field.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return id;
    }

    public Integer getPosition(T entity, Class<?> pc, String positionFieldName) {
        Integer position = null;

        try {
            Field field = pc.getDeclaredField(positionFieldName);
            position = (Integer) field.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return position;
    }

    public void setId(T entity, Class<?> pc, Long id) {
        try {
            Field field = pc.getDeclaredField("id");
            field.set(entity, id);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
