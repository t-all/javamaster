package ru.javamaster.javamaster.models.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Анатация используется для определния полей в фильтрации
 * и используется вместе c Hibernate, при native запросах
 * работать не будет.
 *
 * @author Igor
 * @version 1.0
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {

    /**
     * Служит чтобы указать чтоб поля ссылается на дргой объект
     * используется при связях с Hibernate
     * */
    Class<?> targetEntity() default void.class;
}
