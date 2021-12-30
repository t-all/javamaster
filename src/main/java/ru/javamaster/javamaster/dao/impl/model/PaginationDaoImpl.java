package ru.javamaster.javamaster.dao.impl.model;


import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javamaster.javamaster.dao.impl.reflection.FilterQueryReflection;

import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

public abstract class PaginationDaoImpl<K extends Serializable, T> extends ReadWriteDaoImpl<K, T> {

    @Autowired
    private FilterQueryReflection<T> queryFilter;
    private final String getCountQuery;
    private final String getAllQueryWithWhere;

    protected PaginationDaoImpl() {
        super();
        getCountQuery = "SELECT COUNT(e) FROM " + className + " e";
        getAllQueryWithWhere = "SELECT e FROM " + className + " e WHERE ";
    }

    public List<T> getDataByNumberPage(int pageSize, int pageNumber) {
        return entityManager.createQuery(getAllQuery, persistentClass)
                .setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize).getResultList();
    }

    public List<T> getDataByNumberPageAndFilterPattern(int pageSize, int pageNumber, String filterPattern) {
        if (filterPattern.trim().isEmpty()) {
            return getDataByNumberPage(pageSize, pageNumber);
        }
        TypedQuery<T> query = getFilteredQuery(filterPattern, getAllQueryWithWhere, persistentClass, "e");
        return query.setFirstResult((pageNumber - 1) * pageSize)
                .setMaxResults(pageSize)
                .getResultList();
    }

    public Long getCount() {
        return entityManager.createQuery(getCountQuery, Long.class).getSingleResult();
    }

    public Long getCountByFilterPattern(String filteringPattern) {
        if (filteringPattern.isEmpty()) {
            return getCount();
        }
        return getFilteredQuery(filteringPattern, getCountQuery + " WHERE ", Long.class, "e").getSingleResult();
    }

    public List<T> getDataByNumberPageAndFilterPatternAndParametersMap(int pageSize, int pageNumber, String filterPattern, Map<String, Object> parametersMap) {
        throw new NotYetImplementedException("Required dao's method not implemented.");
    }

    public Long getCountByFilterPatternAndParametersMap(String filterPattern, Map<String, Object> parametersMap) {
        throw new NotYetImplementedException("Required dao's method not implemented.");
    }

    protected <V> TypedQuery<V> getFilteredQuery(String filteringPattern, String queryString, Class<V> type, String alias) {
        String query = queryFilter.getFilteredQuery(filteringPattern, alias, persistentClass);
        if (queryFilter.isExistDateField()) {
            LocalDate date = LocalDate.parse(filteringPattern.trim(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            return entityManager.createQuery(queryString + query, type).setParameter("datePattern", date);
        }
        return entityManager.createQuery(queryString + query, type);
    }
}
