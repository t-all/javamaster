package ru.javamaster.javamaster.dao.impl.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.assertj.core.util.Preconditions;
import org.hibernate.Metamodel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.util.Assert;
import ru.javamaster.javamaster.dao.abstr.model.ReadWriteDao;

import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Iterator;

@Data
@NoArgsConstructor
public abstract class ReadWriteDaoImpl<K extends Serializable, T> implements ReadWriteDao<K, T>, JpaRepository<T,K> {

    private EntityManager entityManager;
    private Class<T> entityClass;

//TODO: Unchecked cast: 'java.lang.reflect.Type' to 'java.lang.Class<T>'
//      Inspection info: Reports the code in which an unchecked warning is issued by the compiler.

//    @SuppressWarnings("unchecked")
    public ReadWriteDaoImpl(Class<T> entityClass, EntityManager entityManager) {
//        this.clazz = (Class<T>) ((ParameterizedType) getClass()
//                .getGenericSuperclass()).getActualTypeArguments()[0];
        this.entityClass = Preconditions.checkNotNull(entityClass);
        this.entityManager = getEntityManager();
    }

    /**
     * @param entity Entity for write to database
     */
    @Override
    public void persist(T entity) {
        Assert.notNull(entity, "Entity must not be null!");

        entityManager.getTransaction().begin();
        entityManager.persist(entity);
        entityManager.getTransaction().commit();
    }

    /**
     * @param entities Entity's for save in database
     */
    @SafeVarargs
    @Override
    public final void persistAll(T... entities) {
        Assert.notNull(entities, "Entities must not be null!");

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
        Assert.notNull(entities, "Entities must not be null!");

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
        Assert.notNull(e, "Entity must not be null!");

        entityManager.getTransaction().begin();
        e = entityManager.merge(e);
        entityManager.getTransaction().commit();
        return e;
    }

//TODO: I doubt the correctness of this approach, but others are not known to me.
    /**
     * @param id Primary key for identification entity in database
     *           (reference to string in table)
     */
    @Override
    public void deleteWithCascadeIgnore(K id) {
        Assert.notNull(id, "ID must not be null!");

// Take entity table name for native query string
        Metamodel metamodel = (Metamodel) entityManager.getMetamodel();
        EntityType<T> entityType = metamodel.entity(entityClass);
        Table table = entityClass.getAnnotation(Table.class);
        String tableName = (table == null) ? entityType.getName().toLowerCase() : table.name();

// Create query string
        StringBuilder queryString = new StringBuilder();
        queryString.append("delete from ").append(tableName).append(" where id = ?");

// Begin transaction and delete
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery(queryString.toString())
                .setParameter(1, id)
                .executeUpdate();
        entityManager.getTransaction().commit();
    }

    /**
     * @param id Primary key for identification entity in database
     *           (reference to string in table)
     */
    @Override
    public void deleteWithCascadeEnable(K id) {
        Assert.notNull(id, "ID must not be null!");

        entityManager.getTransaction().begin();
        entityManager.createQuery("Delete t.type from " + entityClass.getName() + " where t.id = :id").setParameter("id", id).executeUpdate();
        //entityManager.remove(getByKey(id));
        entityManager.getTransaction().commit();
    }

    /**
     * @param entity Entity for delete in database
     *               (string in database table)
     */
    @Override
    public void deleteWithCascadeEnable(T entity) {
        Assert.notNull(entity, "Entity must not be null!");

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
        Assert.notNull(entities, "Entities must not be null!");

        entityManager.getTransaction().begin();
        for (Object entity: entities) {
            entityManager.remove(entity);
        }
        entityManager.getTransaction().commit();
    }

    /**
     * @param entities Collection entity's for update from database
     */
    @Override
    public void updateAll(Iterable<? extends T> entities) {
        Assert.notNull(entities, "Entities must not be null!");

        entityManager.getTransaction().begin();

//        for (T entity: entities) {
//            entityManager.merge(entity);
//        }

        Iterator<? extends T> iterator = entities.iterator();
        while(iterator.hasNext()) {
            T entity = iterator.next();
            entityManager.merge(entity);
        }
        entityManager.getTransaction().commit();
    }

    /**
     * @param fieldName Class field name
     * @param fieldValue The new value of the class field
     * @param id Primary key to find a record in a table
     */
    @Override
    public void updateFieldById(String fieldName, String fieldValue, K id)
            throws NoSuchFieldException, IllegalAccessException {
        Assert.notNull(id, "ID must not be null!");

        entityManager.getTransaction().begin();
        T entity = getByKey(id);
        Field sField = entity.getClass().getField(fieldName);
        sField.set(entity, fieldValue);
        entityManager.merge(entity);
        entityManager.getTransaction().commit();
    }
}
