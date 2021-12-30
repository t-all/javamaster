package ru.javamaster.javamaster.dao.impl.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.javamaster.javamaster.dao.impl.exceptions.PersistException;
import ru.javamaster.javamaster.dao.impl.exceptions.PositionException;
import ru.javamaster.javamaster.dao.impl.reflection.ReflectionHelper;
import ru.javamaster.javamaster.models.annotations.Position;

import javax.persistence.Id;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class PositionableDaoImpl<K extends Serializable, T> extends ReadWriteDaoImpl<K, T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PositionableDaoImpl.class);

    private final String discriminantFieldName;
    private final String positionFieldName;
    private final String idFieldName;

    private final String getPositionByIdQuery;
    private final String getMaxPositionQuery;
    private final String increaseQueryByDiscriminantIdAndSourcePosition;
    private final String reduceQueryByDiscriminantIdAndSourcePosition;
    private final String reduceAmountQueryByDiscriminantIdAndSourcePosition;
    private final String increaseQueryBySourceId;
    private final String reduceQueryByDiscriminantId;
    private final String increaseQueryByDiscriminantIdAndPositionBetween;
    private final String reduceQueryByDiscriminantIdAndPositionBetween;
    private final String reduceAmountQueryByDiscriminantIdAndPositionBetween;

    private final Class<? super T> positionClass;

    protected PositionableDaoImpl() {
        super();

        if (ReflectionHelper.hasClassFieldWithAnnotation(persistentClass, Position.class)) {
            positionClass = persistentClass;
        } else if (ReflectionHelper.hasSuperClass(persistentClass)) {
            positionClass = ReflectionHelper.getSuperClassWithAnnotation(persistentClass, Position.class);
        } else {
            throw new PositionException("В классе " + className + ", или в супер классе этого типа должно присутсвовать поле с аннотацией @Position");
        }

        String positionClassName = positionClass.getSimpleName();
        Field positionField = ReflectionHelper.getAnnotatedField(positionClass, Position.class);

        idFieldName = ReflectionHelper.getAnnotatedField(positionClass, Id.class).getName();
        positionFieldName = positionField.getName();
        discriminantFieldName = positionField.getAnnotation(Position.class).positionDiscriminant();


        getPositionByIdQuery =
                "SELECT pc." + positionFieldName + " FROM " + positionClassName + " pc WHERE pc.id=:sourceId";

        getMaxPositionQuery =
                "SELECT MAX(pc." + positionFieldName + ") FROM " + positionClassName + " pc WHERE pc." + discriminantFieldName + ".id=:discriminantId";

        increaseQueryByDiscriminantIdAndSourcePosition =
                "UPDATE " + positionClassName + " as pc SET " + positionFieldName + " = pc." + positionFieldName + "+1 WHERE pc." +
                        discriminantFieldName + ".id=:discriminantId AND pc." + positionFieldName + " > :sourcePosition";

        reduceQueryByDiscriminantIdAndSourcePosition =
                "UPDATE " + positionClassName + " as pc SET " + positionFieldName + " = pc." + positionFieldName + "-1 WHERE pc." +
                        discriminantFieldName + ".id=:discriminantId AND pc." + positionFieldName + " > :sourcePosition";

        reduceAmountQueryByDiscriminantIdAndSourcePosition =
                "UPDATE " + positionClassName + " as pc SET " + positionFieldName + " = pc." + positionFieldName +
                        "-:reduceAmount WHERE pc." + discriminantFieldName + ".id=:discriminantId " +
                        "AND pc." + positionFieldName + " > :sourcePosition";

        increaseQueryBySourceId =
                "UPDATE " + positionClassName + " as pc SET " + positionFieldName + " = pc." + positionFieldName + "+1 WHERE pc." + discriminantFieldName +
                        ".id= (SELECT pc1." + discriminantFieldName + ".id FROM " + positionClassName + " pc1 WHERE pc1.id=:sourceId) " +
                        "AND pc." + positionFieldName + " > (SELECT pc2." + positionFieldName + " FROM " + positionClassName + " pc2 WHERE pc2.id=:sourceId)";

        reduceQueryByDiscriminantId =
                "UPDATE " + positionClassName + " as pc " +
                        "SET " + positionFieldName + " = pc." + positionFieldName + "-1 " +
                        "WHERE pc." + discriminantFieldName + ".id=:discriminantId " +
                        "AND pc." + positionFieldName + " > :position";

        reduceQueryByDiscriminantIdAndPositionBetween =
                "UPDATE " + positionClassName + " as pc SET " + positionFieldName + " = pc." + positionFieldName + "-1 WHERE pc." + discriminantFieldName +
                        ".id=:discriminantId AND (pc." + positionFieldName + " BETWEEN :fromPosition AND :toPosition OR pc.position = :toPosition)";

        reduceAmountQueryByDiscriminantIdAndPositionBetween =
                "UPDATE " + positionClassName + " as pc SET " + positionFieldName + " = pc." + positionFieldName + "-:reduceAmount" +
                        " WHERE pc." + discriminantFieldName + ".id=:discriminantId AND (pc." + positionFieldName + " " +
                        "BETWEEN :fromPosition AND :toPosition) AND (pc." + positionFieldName + "!= :fromPosition AND pc." + positionFieldName + "!= :toPosition)";

        increaseQueryByDiscriminantIdAndPositionBetween =
                "UPDATE " + positionClassName + " as pc SET " + positionFieldName + " = pc." + positionFieldName + "+1 WHERE pc." + discriminantFieldName +
                        ".id=:discriminantId AND (pc." + positionFieldName + " BETWEEN :fromPosition AND :toPosition OR pc.position = :toPosition)";
    }

    public int getPositionById(K sourceId) {
        Integer result = entityManager.createQuery(getPositionByIdQuery, Integer.class)
                .setParameter("sourceId", sourceId)
                .getSingleResult();

        return result == null ? 0 : result;
    }

    public int getMaxPositionByDiscriminantId(Long discriminantId) {
        Integer result = entityManager.createQuery(getMaxPositionQuery, Integer.class)
                .setParameter("discriminantId", discriminantId)
                .getSingleResult();

        return result == null ? 0 : result;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void reduceAllWhereDiscriminantIdAndPositionIsMoreThan(Long discriminantId, Integer sourcePosition) {
        entityManager.createQuery(reduceQueryByDiscriminantIdAndSourcePosition)
                .setParameter("discriminantId", discriminantId)
                .setParameter("sourcePosition", sourcePosition)
                .executeUpdate();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void reduceAmountAllWhereDiscriminantIdAndPositionIsMoreThan(Long discriminantId, Integer sourcePosition,
                                                                        Integer amount) {
        entityManager.createQuery(reduceAmountQueryByDiscriminantIdAndSourcePosition)
                .setParameter("discriminantId", discriminantId)
                .setParameter("sourcePosition", sourcePosition)
                .setParameter("reduceAmount", amount)
                .executeUpdate();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void increaseAllWhereDiscriminantIdAndPositionIsMoreThan(Long discriminantId, Integer sourcePosition) {
        entityManager.createQuery(increaseQueryByDiscriminantIdAndSourcePosition)
                .setParameter("discriminantId", discriminantId)
                .setParameter("sourcePosition", sourcePosition)
                .executeUpdate();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void reduceAllWherePositionIsMoreThanById(K sourceId) {
        T entity = entityManager.find(persistentClass, sourceId);
        Integer entityPosition = ReflectionHelper.getPosition(entity, positionClass, positionFieldName);
        Object entityDiscriminant = ReflectionHelper.getDiscriminant(entity, positionClass, discriminantFieldName);
        Long discriminantId = ReflectionHelper.getDiscriminantId(entityDiscriminant);
        entityManager.createQuery(reduceQueryByDiscriminantId)
                .setParameter("position", entityPosition)
                .setParameter("discriminantId", discriminantId)
                .executeUpdate();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void increaseAllWherePositionIsMoreThanById(K sourceId) {
        entityManager.createQuery(increaseQueryBySourceId)
                .setParameter("sourceId", sourceId)
                .executeUpdate();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void reduceAllWhereDiscriminantIdAndPositionBetween(Long discriminantId, Integer fromPosition, Integer toPosition) {
        entityManager.createQuery(reduceQueryByDiscriminantIdAndPositionBetween)
                .setParameter("discriminantId", discriminantId)
                .setParameter("fromPosition", fromPosition)
                .setParameter("toPosition", toPosition)
                .executeUpdate();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void reduceAmountAllWhereDiscriminantIdAndPositionBetween(Long discriminantId, Integer fromPosition,
                                                                     Integer toPosition, Integer amount) {
        entityManager.createQuery(reduceAmountQueryByDiscriminantIdAndPositionBetween)
                .setParameter("discriminantId", discriminantId)
                .setParameter("fromPosition", fromPosition)
                .setParameter("toPosition", toPosition)
                .setParameter("reduceAmount", amount)
                .executeUpdate();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void increaseAllWhereDiscriminantIdAndPositionBetween(Long discriminantId, Integer fromPosition, Integer toPosition) {
        entityManager.createQuery(increaseQueryByDiscriminantIdAndPositionBetween)
                .setParameter("discriminantId", discriminantId)
                .setParameter("fromPosition", fromPosition)
                .setParameter("toPosition", toPosition)
                .executeUpdate();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void persist(T entity) {
        Integer position = ReflectionHelper.getPosition(entity, positionClass, positionFieldName);
        if (position != null) {
            throw new PositionException("Сущность не должна иметь заполненное поле position при сохранении");
        }
        Object discriminant = ReflectionHelper.getDiscriminant(entity, positionClass, discriminantFieldName);

        if (discriminant == null) {
            LOGGER.warn("Сущность, предназначенная для использования с позиционированием должна при сохранении иметь заполненное поле дискриминанта позиции. " +
                    "Поле: {}. Полученное значение: null", discriminantFieldName);
            super.persist(entity);
            return;
        }

        Long discriminantId = ReflectionHelper.getDiscriminantId(discriminant);
        int maxPosition = getMaxPositionByDiscriminantId(discriminantId);
        ReflectionHelper.setPosition(entity, positionClass, maxPosition + 1, positionFieldName);
        super.persist(entity);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public T update(T entity) {
        int newPosition = getNewPosition(entity, positionClass, positionFieldName);

        if (newPosition <= 0) {
            throw new PositionException("Сущность должна иметь значение поле position, которое было бы больше 0 при обновлении");
        }

        K entityId = ReflectionHelper.getId(entity, positionClass, idFieldName);
        int oldPosition = getPositionById(entityId);

        if (oldPosition == newPosition) {
            return super.update(entity);
        }

        Object discriminant = ReflectionHelper.getDiscriminant(entity, positionClass, discriminantFieldName);
        Long discriminantId = ReflectionHelper.getDiscriminantId(discriminant);

        int maxPosition = getMaxPositionByDiscriminantId(discriminantId);

        if (maxPosition < newPosition && maxPosition != 0) {
            String message =
                    "Не возможно обновить сущность " + className + ", " +
                            "привязанную к " + discriminant.getClass().getSimpleName() + " (id=" + discriminantId + "). " +
                            "Попытка изменить позицию с (position=" + oldPosition + ") на (position=" + newPosition + "). " +
                            "Позиция сущности должна находиться в диапазоне от 1 до " + maxPosition;
            throw new PositionException(message);
        }

        if (newPosition > oldPosition) {
            reduceAllWhereDiscriminantIdAndPositionBetween(discriminantId, oldPosition + 1, newPosition);
        } else {
            increaseAllWhereDiscriminantIdAndPositionBetween(discriminantId, newPosition, oldPosition - 1);
        }

        return super.update(entity);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void persistAll(T... entities) {
        persistAll(Arrays.asList(entities));
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void persistAll(Collection<T> entities) {
        int maxPosition = getMaxPositionByDiscriminantId(ReflectionHelper.getDiscriminantId(
                ReflectionHelper.getDiscriminant(entities.toArray()[0], positionClass, discriminantFieldName)));
        for (T entity : entities) {
            Integer position = ReflectionHelper.getPosition(entity, positionClass, positionFieldName);
            if (position != null) {
                throw new PositionException("Сущность не должна иметь заполненное поле position при сохранении");
            }
            ReflectionHelper.setPosition(entity, positionClass, ++maxPosition, positionFieldName);
        }
        super.persistAll(entities);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteWithCascadeIgnore(K pk) {
        reduceAllWherePositionIsMoreThanById(pk);
        super.deleteWithCascadeIgnore(pk);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteWithCascadeEnable(K pk) {
        try {
            T entity = (T) entityManager.createQuery(
                    "SELECT pc FROM " + className + " pc JOIN FETCH pc." + discriminantFieldName + " WHERE pc." + idFieldName + "=:id"
            )
                    .setParameter("id", pk)
                    .getSingleResult();
            Object discriminant = ReflectionHelper.getDiscriminant(entity, positionClass, discriminantFieldName);
            Long discriminantId = ReflectionHelper.getDiscriminantId(discriminant);
            Integer position = ReflectionHelper.getPosition(entity, positionClass, positionFieldName);
            reduceAllWhereDiscriminantIdAndPositionIsMoreThan(discriminantId, position);
            entityManager.remove(entity);
        } catch (Exception e) {
            throw new PersistException("Failed to delete an object", e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteWithCascadeEnable(T entity) {
        ReflectionHelper.getDiscriminant(entity, positionClass, discriminantFieldName);
        Object discriminant = ReflectionHelper.getDiscriminant(entity, positionClass, discriminantFieldName);
        Long discriminantId = ReflectionHelper.getDiscriminantId(discriminant);
        Integer position = ReflectionHelper.getPosition(entity, positionClass, positionFieldName);
        reduceAllWhereDiscriminantIdAndPositionIsMoreThan(discriminantId, position);
        super.deleteWithCascadeEnable(entity);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteAll(Collection<T> entities) {
        if (entities != null && !entities.isEmpty()) {
            Object entity = entities.toArray()[0];
            Object discriminant = ReflectionHelper.getDiscriminant(entity, positionClass, discriminantFieldName);
            Long discriminantId = ReflectionHelper.getDiscriminantId(discriminant);
            List<Integer> positions = entities.stream().map(ent -> ReflectionHelper.getPosition(ent, positionClass,
                    positionFieldName))
                    .sorted().collect(Collectors.toList());
            super.deleteAll(entities);
            for (int i = 0; i < positions.size(); i++) {
                if (i > 0) {
                    reduceAmountAllWhereDiscriminantIdAndPositionBetween(discriminantId, positions.get(i - 1),
                            positions.get(i), i);
                }
                if (i == positions.size() - 1) {
                    reduceAmountAllWhereDiscriminantIdAndPositionIsMoreThan(discriminantId, positions.get(i), i + 1);
                }
            }
        }
    }


    @Transactional(propagation = Propagation.MANDATORY)
    public void updatePosition(T entity) {
        Integer newPosition = getNewPosition(entity, positionClass, positionFieldName);

        K entityId = ReflectionHelper.getId(entity, positionClass, idFieldName);
        int oldPosition = getPositionById(entityId);

        if (oldPosition == newPosition) {
            return;
        }

        Object discriminant = ReflectionHelper.getDiscriminant(entity, positionClass, discriminantFieldName);

        Long discriminantId = ReflectionHelper.getDiscriminantId(discriminant);

        int maxPosition = getMaxPositionByDiscriminantId(discriminantId);

        if (maxPosition < newPosition) {
            String message =
                    "Не возможно обновить сущность " + className + ", " +
                            "привязанную к " + discriminant.getClass().getSimpleName() + " (id=" + discriminantId + "). " +
                            "Попытка изменить позицию с (position=" + oldPosition + ") на (position=" + newPosition + "). " +
                            "Позиция сущности должна находиться в диапазоне от 1 до " + maxPosition;
            throw new PositionException(message);
        }

        if (newPosition > oldPosition) {
            reduceAllWhereDiscriminantIdAndPositionBetween(discriminantId, oldPosition + 1, newPosition);
        } else {
            increaseAllWhereDiscriminantIdAndPositionBetween(discriminantId, newPosition, oldPosition - 1);
        }

        entityManager.createQuery("UPDATE " + positionClass.getSimpleName() + " as e SET e.position = :position WHERE e.id = :id")
                .setParameter("position", newPosition)
                .setParameter("id", entityId)
                .executeUpdate();
    }


    private Integer getNewPosition(T entity, Class<? super T> positionClass, String positionFieldName) {
        final Integer newPosition = ReflectionHelper.getPosition(entity, positionClass, positionFieldName);
        if (newPosition == null) {
            throw new PositionException("Сущность должна иметь заполненное поле position при обновлении");
        }

        if (newPosition <= 0) {
            throw new PositionException("Сущность должна иметь значение поле position, которое было бы больше 0 при обновлении");
        }
        return newPosition;
    }
}
