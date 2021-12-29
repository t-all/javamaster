package ru.javamaster.javamaster.dao.impl.user;

import ru.javamaster.javamaster.dao.abstr.user.UserDao;
import ru.javamaster.javamaster.dao.impl.model.PaginationDaoImpl;
import ru.javamaster.javamaster.models.user.User;

public class UserDaoImpl extends PaginationDaoImpl<Long, User> implements UserDao {
}
