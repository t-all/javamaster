package ru.javamaster.javamaster.dao.impl.reflection;

import java.lang.annotation.Annotation;

public class ReflectionHelper {

    boolean hasSuperClass(Class<?> pc) {
//        if(pc.isInstance(pc)) {
//            return true;
//        } else {
//            return false;
//        }

        return pc.isInstance(pc);
    }

    boolean hasClassFieldWithAnnotation(Class<?> pc, Class<? extends Annotation> annotationClass) {
        return false;
    }

//    public Class<? super T> getSuperClassWithAnnotation(Class pc, Class<? extends Annotation> annotationClass) {
//        return null;
//    }
}
