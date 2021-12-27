package ru.javamaster.javamaster.dao.impl.reflection;

import org.apache.commons.lang3.ClassUtils;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;
import ru.javamaster.javamaster.dao.impl.exceptions.ReflectionHelperException;

import javax.persistence.Id;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

public class ReflectionHelper {

    private ReflectionHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static boolean isClassesNamesEquals(Class<?> aClass, Class<?> bClass){
        return aClass.getName().equals(bClass.getName());
    }

    public static boolean hasSuperClass(Class<?> pc) {
        return ClassUtils.getAllSuperclasses(pc).size() > 1;
    }

    public static boolean hasClassFieldWithAnnotation(Class<?> pc, Class<? extends Annotation> annotationClass) {
        Field[] fields = pc.getDeclaredFields();

        boolean hasAnnotation = false;

        for (Field field : fields) {
            if (field.isAnnotationPresent(annotationClass)) {
                hasAnnotation = true;
                break;
            }
        }

        return hasAnnotation;
    }

    public static <T> Class<? super T> getSuperClassWithAnnotation(Class<T> pc, Class<? extends Annotation> annotationClass) {
        List<Class<?>> classList = ClassUtils.getAllSuperclasses(pc);
        Class<? super T> positionNode = null;

        for (Class<?> superClass : classList) {

            if (hasClassFieldWithAnnotation(superClass, annotationClass)) {
                positionNode = (Class<? super T>) superClass;
                break;
            }
        }

        if (positionNode == null)
            throw new ReflectionHelperException("Сущность должна наследоваться от класса с полем, отмеченным аннотацией " + annotationClass.getSimpleName());

        return positionNode;
    }


    public static Field getAnnotatedField(Class<?> pc, Class<? extends Annotation> annotationClass) {
        Field[] fields = pc.getDeclaredFields();

        Field targetField = null;

        for (Field field : fields) {
            if (field.isAnnotationPresent(annotationClass)) {
                targetField = field;
                break;
            }
        }

        return targetField == null ?
                getAnnotatedField(getSuperClassWithAnnotation(pc, annotationClass), annotationClass) : targetField;
    }


    public static <T> Object getDiscriminant(T entity, Class<?> pc, String discriminantFieldName) {
        Field field = null;
        Object discriminant = null;

        entity = (T) initializeIfNeeded(entity);

        try {
            field = pc.getDeclaredField(discriminantFieldName);
        } catch (NoSuchFieldException ignored) {
            //ignoring
        }

        if (field == null) {
            throw new ReflectionHelperException("В сущноcти должно быть поле, отмеченное аннотацией @PositionDiscriminant");
        }

        field.setAccessible(true);
        try {
            discriminant = field.get(entity);
        } catch (IllegalAccessException e) {
            throw new ReflectionHelperException("Не возможно получить доступ к полю, отмеченному аннотацией @PositionDiscriminant в экземпляре класса " + entity.getClass().getName(), e);
        }

        return discriminant;
    }

    private static Field getFieldFromClassRecursive(Class<?> clazz, String fieldName) {
        try {
            return clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class<?> superClass = clazz.getSuperclass();
            if (superClass != null) {
                return getFieldFromClassRecursive(superClass, fieldName);
            } else {
                String message = "Попытка получить поле, отмеченное аннотацией @Id в экземпляре класса " + clazz.getName() +
                        " и его супер классе. Поле отсутствует, или не отмечено аннотацией @Id. \n Возможно, ошибка в типе переданного аргумента в метод getDiscriminantId";
                throw new ReflectionHelperException(message, e);
            }
        }
    }

    public static Long getDiscriminantId(Object discriminant) {
        if (discriminant == null) {
            throw new ReflectionHelperException("Ошибка: в метод getDiscriminantId был передан null аргумент");
        }

        Field field = null;
        Long discriminantId = null;

        discriminant = initializeIfNeeded(discriminant);
        field = getFieldFromClassRecursive(discriminant.getClass(), "id");
        field.setAccessible(true);
        try {
            discriminantId = (Long) field.get(discriminant);
        } catch (IllegalAccessException e) {
            throw new ReflectionHelperException("Не возможно получить доступ к полю, отмеченному аннотацией @Id в экземпляре класса " + discriminant.getClass().getName(), e);
        }

        return discriminantId;
    }

    public static <T> void setPosition(T entity, Class<?> pc, Integer position, String positionFieldName) {
        Field field = null;

        entity = (T) initializeIfNeeded(entity);

        try {
            field = pc.getDeclaredField(positionFieldName);
        } catch (NoSuchFieldException ignored) {
            //ignoring
        }

        if (field == null) {
            throw new ReflectionHelperException("В сущнсоти должно быть поле, отмеченное аннотацией @Position");
        }

        field.setAccessible(true);
        try {
            field.set(entity, position);
        } catch (IllegalAccessException e) {
            throw new ReflectionHelperException("Не возможно получить доступ к полю, отмеченному аннотацией @Position в экземпляре класса " + entity.getClass().getName(), e);
        }
    }

    public static <K, T> K getId(T entity, Class<?> pc, String idFieldName) {
        Field field = null;
        K id = null;

        entity = (T) initializeIfNeeded(entity);

        try {
            field = pc.getDeclaredField(idFieldName);
        } catch (NoSuchFieldException ignored) {
            //ignoring
        }

        if (field == null) {
            throw new ReflectionHelperException("У сущности должно быть поле с названием " + idFieldName);
        }

        field.setAccessible(true);
        try {
            id = (K) field.get(entity);
        } catch (IllegalAccessException e) {
            throw new ReflectionHelperException("Не возможно получить доступ к полю с названием " + idFieldName + " в экземпляре класса " + entity.getClass().getName(), e);
        }

        return id;
    }

    public static <T> Integer getPosition(T entity, Class<?> pc, String positionFieldName) {
        Field field = null;
        Integer position = null;

        entity = (T) initializeIfNeeded(entity);

        try {
            field = pc.getDeclaredField(positionFieldName);
        } catch (NoSuchFieldException ignored) {
            //ignoring
        }

        if (field == null) {
            throw new ReflectionHelperException("В сущнсоти должно быть поле с названием " + positionFieldName);
        }

        field.setAccessible(true);
        try {
            position = (Integer) field.get(entity);
        } catch (IllegalAccessException e) {
            throw new ReflectionHelperException("Не возможно получить доступ к полю с названием " + positionFieldName + " в экземпляре класса " + entity.getClass().getName(), e);
        }

        return position;
    }

    private static Object initializeIfNeeded(Object proxy) {
        Object entity = null;
        if (proxy instanceof HibernateProxy) {
            entity = Hibernate.unproxy(proxy);
        }

        return entity == null ? proxy : entity;
    }

    public static <T> void setId(T entity, Class<?> pc, Long id) {
        Field field = null;

        entity = (T) initializeIfNeeded(entity);

        try {
            field = pc.getDeclaredField(getAnnotatedField(pc, Id.class).getName());
        } catch (NoSuchFieldException ignored) {
            //ignoring
        }

        if (field == null) {
            throw new ReflectionHelperException("В сущнсоти должно быть поле, отмеченное аннотацией @Id");
        }

        field.setAccessible(true);
        try {
            field.set(entity, id);
        } catch (IllegalAccessException e) {
            throw new ReflectionHelperException("Не возможно получить доступ к полю, отмеченному аннотацией @Id в " +
                    "экземпляре класса " + pc.getName(), e);
        }
    }
}

