package ru.javamaster.javamaster.dao.abstr.model;

import java.io.Serializable;

public interface PositionableDao<K extends Serializable, T> extends ReadWriteDao<K, T> {

    int getMaxPositionByDiscriminantId(Long discriminantId);

    void updatePosition(T entity);
}
