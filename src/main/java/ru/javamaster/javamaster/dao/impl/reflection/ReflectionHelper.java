package ru.javamaster.javamaster.dao.impl.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionHelper <T>{


    public boolean hasSuperClass(Class<?> pc) {
        return pc.getClass().isAssignableFrom(pc.getClass());
    }
    public Field getAnnotatedField(Class<?> pc, Class<? extends Annotation> annotationClass){
        Field annotatedField = null;

        try {
            annotatedField = pc.getDeclaredField(annotationClass.getName());
            annotatedField.isAnnotationPresent(annotationClass);
            annotatedField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return annotatedField;
    }

    public Object getDiscriminant(T entity, Class<?> pc, String discriminantFieldName){
        Object discriminant = null;
        try {
            Field field = pc.getDeclaredField(discriminantFieldName);
            field.setAccessible(true);
            discriminant = field.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return discriminant;
    }

    public Long getDiscriminantId(Object discriminant) {
        Long discriminantId = 0L;
        try {
            Method method = discriminant.getClass().getDeclaredMethod("discriminantId");
            method.setAccessible(true);
            method.invoke(discriminant);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return discriminantId;
    }

    public Field getAnnotatedField(Class<?> pc, Class<? extends Annotation> annotationClass){

        return null;
    }
    public Object getDiscriminant(T entity, Class<?> pc, String discriminantFieldName){
        Object discriminant = null;
        try {
            Field field = pc.getDeclaredField(discriminantFieldName);
            field.setAccessible(true);
            discriminant = field.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return discriminant;
    }

    public Long getDiscriminantId(Object discriminant) {
        Long discriminantId = 0L;
        try {
            Method method = discriminant.getClass().getDeclaredMethod("getDiscriminantId");
            method.setAccessible(true);
            method.invoke(discriminant);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return discriminantId;
    }
}



