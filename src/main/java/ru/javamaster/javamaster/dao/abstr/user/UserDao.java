package ru.javamaster.javamaster.dao.abstr.user;

import ru.javamaster.javamaster.dao.abstr.model.PaginationDao;
import ru.javamaster.javamaster.models.user.User;

import java.util.Optional;

public interface UserDao extends PaginationDao<Long, User> {

    Optional<Object> getEmailByUserId(Long studentId);

    Boolean isExistByEmail(String email);

    void changeEnabledById(Long id);

    Optional<Object> getUserByEmail(String email);

    Optional<Object> getIdByEmail(String email);

    void addUser(User user);
}
