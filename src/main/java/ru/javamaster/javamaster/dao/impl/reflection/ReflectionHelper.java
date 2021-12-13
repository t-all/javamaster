package ru.javamaster.javamaster.dao.impl.reflection;

import java.lang.annotation.Annotation;

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
        }
    }
}
