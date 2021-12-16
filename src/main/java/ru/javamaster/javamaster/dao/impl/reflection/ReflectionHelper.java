package ru.javamaster.javamaster.dao.impl.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionHelper <T>{

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
            }
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


