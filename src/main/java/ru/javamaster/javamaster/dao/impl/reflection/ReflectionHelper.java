package ru.javamaster.javamaster.dao.impl.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ReflectionHelper<T> {

    public boolean hasSuperClass(Class<?> pc) {
        return pc.getClass().isAssignableFrom(pc.getClass());
    }

    public boolean hasClassFieldWithAnnotation(Class<?> pc, Class<? extends Annotation> annotationClass) {
        return pc.isAnnotationPresent(annotationClass);
    }

    public Class<? super T> getSuperClassWithAnnotation(Class pc, Class<? extends Annotation> annotationClass) {

        if (pc.isAnnotationPresent(annotationClass)) {
            return pc.getSuperclass();
        } else {
            return null;
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
