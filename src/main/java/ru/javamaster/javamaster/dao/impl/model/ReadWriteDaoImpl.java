package ru.javamaster.javamaster.dao.impl.model;

import org.hibernate.HibernateException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javamaster.javamaster.dao.impl.exceptions.MergeException;
import ru.javamaster.javamaster.dao.impl.exceptions.PersistException;
import ru.javamaster.javamaster.dao.impl.exceptions.RemoveException;

import java.io.Serializable;
import java.util.Collection;

/**
 * На всех методах, подразумевающих insert/update/delete запросы в базу ставятся @Transactional(propagation = Propagation.MANDATORY)
 * Методы чтения не являются транзакционными, и не требуют открытых транзакций для работы
 * http://akorsa.ru/2017/01/sovety-i-oshibki-v-spring-transactions/
 */


public abstract class ReadWriteDaoImpl<K extends Serializable, T> extends ReadOnlyDaoImpl<K, T> {

    @Transactional(propagation = Propagation.MANDATORY)
    public void persist(T entity) {
        try {
            entityManager.persist(entity);
        } catch (HibernateException e) {
            throw new PersistException("Failed to add an object", e);
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public T update(T entity) {
        T mergedEntity;
        try {
            mergedEntity = entityManager.merge(entity);
        } catch (HibernateException e) {
            throw new MergeException("Failed to merge an object", e);
        }

        return mergedEntity;
    }

    @SuppressWarnings("Duplicates")
    @Transactional(propagation = Propagation.MANDATORY)
    public void persistAll(T... entities) {
        try {
            int i = 0;
            for (T entity : entities) {
                if (i > 0 && i % 1000 == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(entity);
                i++;
            }
        } catch (HibernateException e) {
            throw new PersistException("Failed to add an object", e);
        }
    }

    @SuppressWarnings("Duplicates")
    @Transactional(propagation = Propagation.MANDATORY)
    public void persistAll(Collection<T> entities) {
        try {
            int i = 0;
            for (T entity : entities) {
                if (i > 0 && i % 1000 == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
                entityManager.persist(entity);
                i++;
            }
        } catch (HibernateException e) {
            throw new PersistException("Failed to add an object", e);
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteWithCascadeIgnore(K pk) {
        try {
            entityManager.flush();
            entityManager.clear();
            entityManager.createQuery("DELETE " + className + " WHERE  id=:Id")
                    .setParameter("Id", pk)
                    .executeUpdate();
        } catch (HibernateException e) {
            throw new PersistException("Failed to delete an object", e);
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteWithCascadeEnable(K pk) {
        try {
            T proxyObject = entityManager.getReference(persistentClass, pk);
            entityManager.remove(proxyObject);
        } catch (HibernateException e) {
            throw new PersistException("Failed to delete an object", e);
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteWithCascadeEnable(T entity) {
        try {
            entityManager.remove(entityManager.contains(entity) ? entity : entityManager.merge(entity));
        } catch (HibernateException e) {
            throw new RemoveException("Failed to deleting an object", e);
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteAll(Collection<T> entities) {
        for (T entity : entities) {
            try {
                entityManager.remove(entity);
            } catch (HibernateException e) {
                throw new RemoveException("Failed to deleting an object", e);
            }
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void updateAll(Iterable<? extends T> entities) {
        for (T entity : entities) {
            try {
                entityManager.merge(entity);
            } catch (HibernateException e) {
                throw new RemoveException("Failed to update object ", e);
            }
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void updateFieldById(String fieldName, String fieldValue, long id) {
        entityManager.createQuery("UPDATE " + className + " SET " + fieldName + " = :val WHERE id = :id")
                .setParameter("val", fieldValue)
                .setParameter("id", id)
                .executeUpdate();
    }

}
