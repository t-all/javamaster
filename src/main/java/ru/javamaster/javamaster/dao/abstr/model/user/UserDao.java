package ru.javamaster.javamaster.dao.abstr.model.user;


import ru.javamaster.javamaster.dao.abstr.model.PaginationDao;
import ru.javamaster.javamaster.models.user.User;

public interface UserDao extends PaginationDao<Long, User> {

}
