package ru.javamaster.javamaster.dao.impl.model;

import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.javamaster.javamaster.dao.abstr.model.ReadWriteDao;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

@Data
public abstract class ReadWriteDaoImpl<K extends Serializable, T> implements ReadWriteDao<K, T>, JpaRepository<T,K> {

    private EntityManager entityManager;
    private Class<T> clazz;

//    public void setClazz(final Class<T> clazzToSet) {
//        clazz = Preconditions.checkNotNull(clazzToSet);
//    }

//TODO: Unchecked cast: 'java.lang.reflect.Type' to 'java.lang.Class<T>'
//      Inspection info: Reports the code in which an unchecked warning is issued by the compiler.

    @SuppressWarnings("unchecked")
    public ReadWriteDaoImpl() {
        this.clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.entityManager = getEntityManager();
    }

    /**
     * @param entity Entity for write to database
     */
    @Override
    public void persist(T entity) {
        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
        save(entity);
    }

    /**
     * @param entities Entity's for save in database
     */
    @SafeVarargs
    @Override
    public final void persistAll(T... entities) {
        entityManager.getTransaction().begin();
        for (T entity : entities) {
            entityManager.persist(entity);
        }
        entityManager.getTransaction().commit();
    }

    /**
     * @param entities Collection entity's for save in database
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void persistAll(Collection entities) {
        entityManager.getTransaction().begin();
        for (Object entity: entities) {
            entityManager.persist(entity);
        }
        entityManager.getTransaction().commit();
    }

    /**
     * @param e Entity for update in database
     * @return Returns a refreshed entity from the database
     */
    @Override
    public T update(T e) {
        entityManager.getTransaction().begin();
        e = entityManager.merge(e);
        entityManager.getTransaction().commit();
        return e;
    }
//TODO: You need to check on another project!
    /**
     * @param id Primary key for identification entity in database
     *           (reference to string in table)
     */
    @Override
    public void deleteWithCascadeIgnore(K id) {
        entityManager.getTransaction().begin();
        entityManager.createQuery("Delete t.type from " + clazz.getName() + " where t.id = :id", clazz);
        entityManager.getTransaction().commit();
    }

    /**
     * @param id Primary key for identification entity in database
     *           (reference to string in table)
     */
    @Override
    public void deleteWithCascadeEnable(K id) {
        entityManager.getTransaction().begin();
        entityManager.remove(getByKey(id));
        entityManager.getTransaction().commit();
    }

    /**
     * @param entity Entity for delete in database
     *               (string in database table)
     */
    @Override
    public void deleteWithCascadeEnable(T entity) {
        entityManager.getTransaction().begin();
        entityManager.remove(entity);
        entityManager.getTransaction().commit();
    }

    /**
     * @param entities Collection entity's for delete from database
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void deleteAll(Collection entities) {
        entityManager.getTransaction().begin();
        for (Object entity: entities) {
            entityManager.remove(entity);
        }
        entityManager.getTransaction().commit();
    }

    /**
     * @param entities
     */
    @Override
    public void updateAll(Iterable<? extends T> entities) {

    }

    /**
     * @param fieldName
     * @param fieldValue
     * @param id
     */
    @Override
    public void updateFieldById(String fieldName, String fieldValue, long id) {

    }
}
