package ru.javamaster.javamaster.dao.abstr.model;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public interface ReadOnlyDao <K extends Serializable, T>{
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

//    @Override
//    public boolean isExistById(Serializable id) {
//
//        return (boolean) entityManager.createQuery("SELECT t.type FROM" + clazz.getName() + " t WHERE u.id=:id")
//                .setParameter("id", id)
//                .getSingleResult();
//    }
