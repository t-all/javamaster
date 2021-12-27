package ru.javamaster.javamaster.dao.impl.reflection;


import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import ru.javamaster.javamaster.dao.impl.exceptions.NotSelectField;
import ru.javamaster.javamaster.models.annotations.Ordered;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Класс служит для создания запроса фильтрации на основе рефликсии
 * использвутеся с анатациями @Field и @Ordered
 *
 * @author Igor
 * @version 1.0
 */
@Component
public class FilterQueryReflection<T> {

    private static final int LAST_SYMBOL = 1;

    private final AtomicBoolean isExistDateField = new AtomicBoolean(false);

    /**
     * Главный метод при создании запроса, который состоит из трех составляющих:
     * 1) bodyFilter - блок полей, которые указаны с помощью аннотация @Field
     * 2) dateFilter - блок полей если указана в @param filteringPattern дата
     * 3) ordered - блок полей которые служат для сортировки, если указана аннотация @Ordered
     *
     * @param filteringPattern - Источник по которому происходит фильтрация и задается пользователям
     * @param alias            - Псевдоним отображающий Entity
     * @param persistentClass  - Класс у которого будут браться поля с помощью Reflection
     * @throws NotSelectField - Если поля не указаны с помощью аннотация @Field, то кинет исключение
     */
    public String getFilteredQuery(String filteringPattern, String alias, Class<T> persistentClass) {

        StringBuilder bodyFilter = new StringBuilder();
        StringBuilder dateFilter = new StringBuilder();
        StringBuilder ordered = new StringBuilder();

        AtomicBoolean isSelectField = new AtomicBoolean(false);

        ReflectionUtils.doWithFields(persistentClass, field -> {

            Class<?> rootClass = field.getAnnotation(ru.javamaster.javamaster.models.annotations.Field.class).targetEntity();

            if (rootClass != void.class) {
                searchFieldInBody(rootClass, field.getName(), bodyFilter, alias);
                searchFieldInOrdered(rootClass, field.getName(), ordered, alias);
            } else {
                checkBodyOrDateField(bodyFilter, dateFilter, alias, field, filteringPattern);
            }
            if (field.isAnnotationPresent(Ordered.class)) {
                String typeOrder = field.getAnnotation(Ordered.class).type().name();
                ordered.append(alias).append(".").append(field.getName()).append(" ").append(typeOrder).append(",");
            }

            isSelectField.set(true);

        }, field -> field.isAnnotationPresent(ru.javamaster.javamaster.models.annotations.Field.class));

        if (!isSelectField.get()) {
            throw new NotSelectField("Use the @Field annotation to select the required fields in " + persistentClass);
        }

        return getResultQuery(filteringPattern, dateFilter.toString(), bodyFilter.toString(), ordered.toString());
    }

    private void searchFieldInBody(Class<?> source, String root, StringBuilder query, String alias) {

        ReflectionUtils.doWithFields(source, field -> {

            String change = alias + "." + root + "." + field.getName();

            if (field.getAnnotation(ru.javamaster.javamaster.models.annotations.Field.class).targetEntity() != void.class) {
                searchFieldInBody(field.getAnnotation(ru.javamaster.javamaster.models.annotations.Field.class).targetEntity(), field.getName(), query, alias);
            } else {
                query.append(change).append(",");
            }

        }, field -> field.isAnnotationPresent(ru.javamaster.javamaster.models.annotations.Field.class));

    }

    private void searchFieldInOrdered(Class<?> source, String root, StringBuilder query, String alias) {
        ReflectionUtils.doWithFields(source, field -> {

            Class<?> rootClass = field.getAnnotation(ru.javamaster.javamaster.models.annotations.Field.class).targetEntity();
            if (rootClass != void.class) {
                searchFieldInOrdered(rootClass, field.getName(), query, alias);
            }

            if (field.isAnnotationPresent(Ordered.class)) {
                String typeOrder = field.getAnnotation(Ordered.class).type().name();
                query.append(alias).append(".").append(root).append(".").append(field.getName()).append(" ").append(typeOrder).append(",");
            }

        }, field -> field.isAnnotationPresent(Ordered.class));
    }

    //TODO так как в базе храниться формата даты в таком виде "1999-01-01", а на странице пользователя формат даты "01.01.1999",
    // то приходится выносить дату в отдельный блок запроса, а так же при при
    // фильтрации 01.01 - не будет работать так как не подходит под формат даты и в базе хранится 01-01
    private String checkDateType(String alias, Field field, String filterPattern, boolean isFirst) {

        if ((field.getType() == LocalDate.class || Date.class == field.getType()) && isDate(filterPattern)) {

            if (isFirst) {
                return alias + "." + field.getName() + " LIKE :datePattern";
            } else {
                return " OR " + alias + "." + field.getName() + " LIKE :datePattern";
            }

        } else {
            return "";
        }
    }

    private boolean isDate(String filterPattern) {
        return filterPattern.matches("^\\s*(3[01]|[12][0-9]|0?[1-9])\\.(1[012]|0?[1-9])\\.((?:19|20)\\d{2})\\s*$");
    }

    private String getResultQuery(String filteringPattern, String dateFilter, String bodyFilter, String ordered) {

        filteringPattern = filteringPattern.trim();

        bodyFilter = bodyFilter.substring(0, bodyFilter.length() - LAST_SYMBOL);

        if (!ordered.isEmpty()) {
            ordered = " ORDER BY " + ordered.substring(0, ordered.length() - LAST_SYMBOL);
        }

        String filterQuery = " CONCAT(" + bodyFilter + ") LIKE '%" + filteringPattern + "%'" + ordered;

        if (!dateFilter.isEmpty()) {
            isExistDateField.set(true);
            return " " + dateFilter + " OR " + filterQuery;
        }
        isExistDateField.set(false);
        return filterQuery;
    }

    private void checkBodyOrDateField(StringBuilder bodyFilter, StringBuilder dateFilter, String alias, Field field, String filteringPattern) {
        String localQueryDate = checkDateType(alias, field, filteringPattern, dateFilter.toString().isEmpty());
        if (localQueryDate.isEmpty()) {
            bodyFilter.append(alias).append(".").append(field.getName()).append(",");
        } else {
            dateFilter.append(localQueryDate);
        }
    }

    public boolean isExistDateField() {
        return isExistDateField.get();
    }

}
