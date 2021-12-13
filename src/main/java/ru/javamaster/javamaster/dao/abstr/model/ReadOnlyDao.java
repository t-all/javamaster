package ru.javamaster.javamaster.dao.abstr.model;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;

public interface ReadOnlyDao<K extends Serializable, T> {
<<<<<<< HEAD

=======
>>>>>>> 9cdf8b4413b0cc3aeffa8b5800c050235d93cf93
    T getByKey(K id);

    T getProxy(K id);

    List getAll();

    EntityManager getEntityManager();

    boolean isExistById(K id);

    void refresh(T entity);

    List getAllByIds(Iterable ids);

    boolean isExistAllByIds(K[] ids);

    List getByField(String fieldName, String fieldValue);
<<<<<<< HEAD

=======
>>>>>>> 9cdf8b4413b0cc3aeffa8b5800c050235d93cf93
}
