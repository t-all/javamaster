package ru.javamaster.javamaster.dao.abstr.user;

import ru.javamaster.javamaster.dao.abstr.model.PaginationDao;
import ru.javamaster.javamaster.models.user.User;

import java.util.Optional;

public interface UserDao extends PaginationDao<Long, User> {

    Optional getEmailByUserId(Long studentId);

    Boolean isExistByEmail(String email);

    void changeEnabledById(Long id);

    Optional getUserByEmail(String email);

    Optional getIdByEmail(String email);
}
