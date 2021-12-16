package ru.javamaster.javamaster.dao.impl.model;

import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import ru.javamaster.javamaster.dao.abstr.model.ReadWriteDao;

import javax.persistence.EntityManager;
import javax.persistence.OneToMany;
import javax.persistence.PersistenceException;
import javax.persistence.Table;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import java.io.Serializable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

//connect logger from lombok
@Slf4j
public abstract class ReadWriteDaoImpl<K extends Serializable, T> extends ReadOnlyDaoImpl <K, T> implements ReadWriteDao<K, T> {

    private static final int BATCH_SIZE = 500;

    public ReadWriteDaoImpl() {
        super();
    }

    /**
     * @param entity Entity for write to database
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void persist(T entity) {
        Assert.notNull(entity, "Entity must not be null!");

        try {
            entityManager.persist(entity);
        } catch (PersistenceException e) {
            log.warn("IN method persist(T entity) error -> {}", e.getMessage());
        }
    }

    /**
     * @param entities Entity's for save in database
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void persistAll(T... entities) {
        Assert.notEmpty(entities, "Entities must not be null!");

        try {
            int i = 0;
            for (T entity : entities) {
                entityManager.persist(entity);
                i++;
                if(i % BATCH_SIZE == 0){
                    entityManager.flush();
                    entityManager.clear();
                    log.info("IN method persistAll(T... entities) batch entities was flushed to database ... ");
                }
            }
        } catch (PersistenceException e) {
            log.warn("IN method persistAll(T... entities) error -> {}", e.getMessage());
        }
    }

    /**
     * @param entities Collection entity's for save in database
     */
    @SuppressWarnings("rawtypes")
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void persistAll(Collection entities) {
        Assert.notEmpty(entities, "Entities must not be null!");

        try {
            int i = 0;
            for (Object entity: entities) {
                entityManager.persist(entity);
                i++;
                if(i % BATCH_SIZE == 0){
                    entityManager.flush();
                    entityManager.clear();
                    log.info("IN method persistAll(Collection entities) batch entities was flushed to database ... ");
                }
            }
        } catch (PersistenceException e) {
            log.warn("IN method persistAll(Collection entities) error -> {}", e.getMessage());
        }
    }

    /**
     * @param e Entity for update in database
     * @return Returns a refreshed entity from the database
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public T update(T e) {
        Assert.notNull(e, "Entity must not be null!");

        try {
            e = entityManager.merge(e);
        } catch (Exception ex) {
            log.warn("IN method T update(T e) error -> {}", ex.getMessage());
        }
        return e;
    }

    /**
     * @param id Primary key for identification entity in database
     *           (reference to string in table)
     */
    @Override
    public void deleteWithCascadeIgnore(K id){
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
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteWithCascadeEnable(K id) {
        Assert.notNull(id, "ID must not be null!");

        try {
            entityManager.createQuery("Delete t.type from " + persistentClass.getName() + " where t.id = :id")
                    .setParameter("id", id)
                    .executeUpdate();
        } catch (PersistenceException e) {
            log.warn("IN method deleteWithCascadeEnable(K id) error -> {}", e.getMessage());
        }
    }

    /**
     * @param entity Entity for delete in database
     *               (string in database table)
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteWithCascadeEnable(T entity) {
        Assert.notNull(entity, "Entity must not be null!");

        try {
            entityManager.remove(entity);
        } catch (PersistenceException e) {
            log.warn("IN method deleteWithCascadeEnable(T entity) error -> {}", e.getMessage());
        }
    }

    /**
     * @param entities Collection entity's for delete from database
     */
    @SuppressWarnings("rawtypes")
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteAll(Collection entities) {
        Assert.notEmpty(entities, "Entities must not be null!");

        try {
            for (Object entity: entities) {
                Assert.notNull(entity, "Entity must not be null!");
                entityManager.remove(entity);
            }
        } catch (PersistenceException e) {
            log.warn("IN method deleteAll(Collection entities) error -> {}", e.getMessage());
        }
    }

    /**
     * @param entities Collection entity's for update from database
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void updateAll(Iterable<? extends T> entities) {
        Assert.notNull(entities, "Entities must not be null!");

        try {
            Iterator<? extends T> iterator = entities.iterator();
            int i = 0;
            while(iterator.hasNext()) {
                T entity = iterator.next();
                Assert.notNull(entity, "Entity must not be null!");
                entityManager.persist(entity);
                i++;
                if(i % BATCH_SIZE == 0){
                    entityManager.flush();
                    entityManager.clear();
                    log.info("IN method updateAll(Iterable<? extends T> entities) batch entities was flushed to database ... ");
                }
            }
        } catch (PersistenceException e) {
            log.warn("IN method updateAll(Iterable<? extends T> entities) error -> {}", e.getMessage());
        }
    }

    /**
     * @param fieldName Class field name
     * @param fieldValue The new value of the class field
     * @param id Primary key to find a record in a table
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void updateFieldById(String fieldName, String fieldValue, K id) {
        Assert.notNull(id, "ID must not be null!");

        try {
            entityManager.createQuery("update " + persistentClass.getName() + " a set " + fieldName + " = :fieldValue where a.id = :id")
                    .setParameter("fieldValue", fieldValue)
                    .setParameter("id", id)
                    .executeUpdate();
        } catch (PersistenceException e) {
            log.warn("IN method updateFieldById(String fieldName, String fieldValue, K id) error -> {}", e.getMessage());
        }
    }

    /**
     * @param entityClass The class from which you want to get a list of annotated fields
     * @param annotationClasses Annotation's which field should be marked
     * @return List of class fields marked with the passed annotation
     */
    @SafeVarargs
    private List<Field> getAnnotationFields(T entityClass, Class<? extends Annotation> ... annotationClasses){

        log.info("Running util method - getAnnotationFields");
        List<Field> fields = new ArrayList<>();

        try {
            Arrays.stream(annotationClasses).collect(Collectors.toList()).forEach(annotationClass -> {
                for(Field field : entityClass.getClass().getDeclaredFields()) {
                    if (field.getAnnotation(annotationClass) != null) {
                        fields.add(field);
                    }
                }
            });
        } catch (SecurityException | NullPointerException e) {
            log.warn("IN util method getAnnotationFields error -> {}", e.getMessage());
        }
        return fields;
    }

    /**
     * @param entityManager Entity manager
     * @param entityClass Entity class for which you need to determine the name of the table in the database
     * @return The name of the table in the database
     */
    private String getTableName(EntityManager entityManager, Class<T> entityClass) {
        /*
         * Check if the specified class is present in the metamodel.
         * Throws IllegalArgumentException if not.
         * Check whether @Table annotation is present on the class.
         */
        log.info("Running util method - getTableName");
        Table t = null;
        EntityType<T> entityType = null;
        try {
            Metamodel meta = entityManager.getMetamodel();
            entityType = meta.entity(entityClass);
            t = entityClass.getAnnotation(Table.class);
        } catch (IllegalArgumentException | NullPointerException e) {
            log.warn("IN util method getTableName error -> {}", e.getMessage());
        }
        if ((t == null)) {
            Assert.notNull(entityType, "ID must not be null!");
            return entityType.getName().toLowerCase();
        } else {
            return t.name();
        }
    }
}
