package ru.javamaster.javamaster.dao.abstr.model;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public interface ReadOnlyDao<K extends Serializable, T> {

    T getByKey(K id);

    T getProxy(K id);

    List getAll();

    EntityManager getEntityManager();

    boolean isExistById(K id);

    void refresh(T entity);

    List getAllByIds(Iterable ids);

    boolean isExistAllByIds(K[] ids);

    List getByField(String fieldName, String fieldValue);

}
