package ru.javamaster.javamaster.dao.impl.user;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javamaster.javamaster.dao.abstr.user.UserDao;
import ru.javamaster.javamaster.dao.impl.model.PaginationDaoImpl;
import ru.javamaster.javamaster.models.user.User;

import java.util.Optional;

@Repository
@Transactional
public class UserDaoImpl extends PaginationDaoImpl<Long, User> implements UserDao {

    @Override
    public Optional<Object> getEmailByUserId(Long studentId) {
        return Optional.of(entityManager.createQuery("SELECT u.email FROM User u WHERE u.id=:id")
                .setParameter("id", studentId)
                .getSingleResult());
    }

    @Override
    public Boolean isExistByEmail(String email) {
        return entityManager.createQuery("SELECT CASE WHEN COUNT(u.email) > 0 THEN true ELSE false END FROM User u WHERE u.email=:email", Boolean.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public void changeEnabledById(Long id) {
        entityManager.createQuery("UPDATE User SET enable = CASE WHEN enable='true' THEN false ELSE true END WHERE id=:id")
                .setParameter("id", id)
                .executeUpdate();
    }

    @Override
    public Optional<Object> getUserByEmail(String email) {
        return Optional.of(entityManager.createQuery("SELECT u FROM User u WHERE u.email=:email")
                .setParameter("email", email)
                .getSingleResult());
    }

    @Override
    public Optional<Object> getIdByEmail(String email) {
        return Optional.of(entityManager.createQuery("SELECT u.id FROM User u WHERE u.email=:email")
                .setParameter("email", email)
                .getSingleResult());
    }

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }
}