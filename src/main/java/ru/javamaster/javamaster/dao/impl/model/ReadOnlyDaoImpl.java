package ru.javamaster.javamaster.dao.impl.model;

import org.springframework.stereotype.Repository;
import ru.javamaster.javamaster.dao.abstr.model.ReadOnlyDao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Repository
public class ReadOnlyDaoImpl<K extends Serializable, T> implements ReadOnlyDao {

    private Class<T> aClass;

    @PersistenceContext
    private EntityManager entityManager;

    public ReadOnlyDaoImpl(Class<T> aClass, EntityManager entityManager) {
        this.aClass = aClass;
        this.entityManager = entityManager;
    }

    @Override
    public T getByKey(Serializable id) {
        return entityManager.find(aClass, id);
    }

    @Override
    public T getProxy(Serializable id) {
        return entityManager.find(aClass, id);
    }

    @Override
    public List<T> getAll() {
        return entityManager.createQuery("FROM User", aClass).getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public boolean isExistById(Serializable id) {
        return (boolean) entityManager.createQuery("SELECT u FROM " + aClass.getName() + "u WHERE u.id=:id")
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public void refresh(Object entity) {
        entityManager.refresh(entity);
    }

    @Override
    public List getAllByIds(Iterable ids) {
        return entityManager.createQuery("SELECT u FROM " + aClass.getName() + "u WHERE u.ids=:ids")
                .setParameter("ids", ids)
                .getResultList();
    }

    // не понимаю, какой должен быть результат
    @Override
    public boolean isExistAllByIds(Serializable[] ids) {
        return false;
    }

    @Override
    public List getByField(String fieldName, String fieldValue) {
        return entityManager.createQuery("SELECT u FROM " + aClass.getName() + " WHERE u." + fieldName + "=:value")
                .setParameter("value", fieldValue)
                .getResultList();
    }
}