package ru.javamaster.javamaster.dao.impl.user;

import ru.javamaster.javamaster.dao.abstr.user.UserDao;
import ru.javamaster.javamaster.dao.impl.model.PaginationDaoImpl;
import ru.javamaster.javamaster.models.user.User;

import java.util.Optional;

public class UserDaoImpl extends PaginationDaoImpl<Long, User> implements UserDao {

    @Override
    public Optional getEmailByUserId(Long studentId) {
        return (Optional) entityManager.createQuery("SELECT u.email FROM User u WHERE u.id=:id")
                .setParameter("id", studentId)
                .getSingleResult();
    }

    @Override
    public Boolean isExistByEmail(String email) {
        return entityManager.createQuery("SELECT EXISTS (SELECT email FROM User WHERE email=:email)", Boolean.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public void changeEnabledById(Long id) {
        entityManager.createQuery("UPDATE User SET enable = NOT enable WHERE id=:id")
                .setParameter("id", id)
                .getSingleResult();

    }

    @Override
    public Optional getUserByEmail(String email) {
        return (Optional) entityManager.createQuery("SELECT u FROM User u WHERE u.email=:email")
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public Optional getIdByEmail(String email) {
        return (Optional) entityManager.createQuery("SELECT u.id FROM User u WHERE u.email=:email")
                .setParameter("email", email)
                .getSingleResult();
    }
}