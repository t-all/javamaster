package ru.javamaster.javamaster.models.annotations;

import ru.javamaster.javamaster.models.enums.OrderedType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация используется вместе с аннотацией @Field,
 * если аннотация @Field не указана, то @Ordered работать не будет.
 *
 * Позволяет задать сортировку по полю или полям, в запросе будет
 * предствлять ORDER BY
 *
 * @version 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Ordered {

    /**
     * Задает тип сортировки по убыванию или возрастанию
     * по умолчанию используется по-возрастанию
     * */
    OrderedType type() default OrderedType.ASC;
}
