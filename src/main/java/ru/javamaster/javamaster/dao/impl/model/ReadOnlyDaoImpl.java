package ru.javamaster.javamaster.dao.impl.model;


import org.hibernate.HibernateException;
import ru.javamaster.javamaster.dao.impl.exceptions.MergeException;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class ReadOnlyDaoImpl<K extends Serializable, T> {

    protected final Class<T> persistentClass;
    protected final String getAllQuery;
    private final String getQueryWhere;
    @PersistenceContext
    protected EntityManager entityManager;
    protected String genericClassName;
    protected String className;

    @SuppressWarnings("unchecked")
    protected ReadOnlyDaoImpl() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        genericClassName = persistentClass.toGenericString();
        className = genericClassName.substring(genericClassName.lastIndexOf('.') + 1);
        getAllQuery = "FROM " + className;
        getQueryWhere = "SELECT e FROM " + className + " e WHERE e.";
    }

    public T getByKey(K key) {
        return entityManager.find(persistentClass, key);
    }

    public T getProxy(K key) {
        return entityManager.getReference(persistentClass, key);
    }

    public List<T> getAll() {
        return entityManager.createQuery(getAllQuery, persistentClass).getResultList();
    }

    public T getByKeyWithFetchGraph(K id, String graphName) {
        EntityGraph<?> graph = entityManager.getEntityGraph(graphName);

        Map<String, Object> hints = new HashMap<>();
        hints.put("javax.persistence.fetchgraph", graph);

        return entityManager.find(persistentClass, id, hints);

    }

    public List<T> getByField(String fieldName, String fieldValue) {
            return entityManager.createQuery(getQueryWhere + fieldName + " = :val", persistentClass)
                    .setParameter("val", fieldValue)
                    .getResultList();
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public boolean isExistById(K id) {
        return entityManager.createQuery(
                "SELECT CASE WHEN COUNT(t) > 0 THEN true ELSE false END FROM " + className + " t WHERE t.id =: id"
                , Boolean.class
        ).setParameter("id", id)
                .getSingleResult();
    }

    public void refresh(T entity) {
        try {
            entityManager.refresh(entity);
        } catch (HibernateException e) {
            throw new MergeException("Failed to refresh an object", e);
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> getAllByIds(Iterable<K> ids) {
        return entityManager.createQuery("SELECT t FROM " + className + " t WHERE t.id IN :ids")
                .setParameter("ids", ids)
                .getResultList();
    }

    public boolean isExistAllByIds(K[] ids) {
        return entityManager.createQuery("SELECT COUNT(t.id) FROM " + className + " t WHERE t.id IN :ids", Long.class)
                .setParameter("ids", Arrays.asList(ids)).getSingleResult() == ids.length;
    }
}
