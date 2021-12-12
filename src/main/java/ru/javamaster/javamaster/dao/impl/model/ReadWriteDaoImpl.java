package ru.javamaster.javamaster.dao.impl.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.assertj.core.util.Preconditions;
import org.springframework.util.Assert;
import ru.javamaster.javamaster.dao.abstr.model.ReadWriteDao;

import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@Data
@NoArgsConstructor
public abstract class ReadWriteDaoImpl<K extends Serializable, T> implements ReadWriteDao<K, T> {

    private EntityManager entityManager;
    private Class<T> entityClass;

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
        Assert.notEmpty(entities, "Entities must not be null!");

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
        Assert.notEmpty(entities, "Entities must not be null!");

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
    public void deleteWithCascadeIgnore(K id) throws NoSuchFieldException, IllegalAccessException {
        Assert.notNull(id, "ID must not be null!");
        // Entity for delete
        T deletedEntity = getByKey(id);
        // Take list fields with Annotation @OneToMany
        List<Field> fields = getAnnotationFields(deletedEntity, OneToMany.class);

        if(!fields.isEmpty()){
            for (Field f: fields) {
        // Overwriting foreign keys, replacing field values with null
                updateFieldById(f.getName(), null, id);
            }
        }
        deleteWithCascadeEnable(id);
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
        Assert.notEmpty(entities, "Entities must not be null!");

        entityManager.getTransaction().begin();
        for (Object entity: entities) {
            Assert.notNull(entity, "Entity must not be null!");
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
            Assert.notNull(entity, "Entity must not be null!");
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

    /**
     * @param entityClass The class from which you want to get a list of annotated fields
     * @param annotationClass Annotation which field should be marked
     * @return List of class fields marked with the passed annotation
     */
    public List<Field> getAnnotationFields(T entityClass, Class<? extends Annotation> annotationClass){
        ArrayList<Field> fields = new ArrayList<>();
        for(Field field : entityClass.getClass().getDeclaredFields()) {
            if (field.getAnnotation(annotationClass) != null) {
                fields.add(field);
            }
        }
        return fields;
    }
}
