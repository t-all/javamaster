package ru.javamaster.javamaster.dao.abstr.model;

import java.util.List;
import java.util.Optional;

public interface DtoDao<K, T> {
    Optional<T> getByEntityId(K id);

    List<T> getAll();
}