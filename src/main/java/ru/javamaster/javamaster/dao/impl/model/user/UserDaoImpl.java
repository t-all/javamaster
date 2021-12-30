package ru.javamaster.javamaster.dao.impl.model.user;

import org.springframework.stereotype.Repository;
import ru.javamaster.javamaster.dao.abstr.model.user.UserDao;
import ru.javamaster.javamaster.dao.impl.model.PaginationDaoImpl;
import ru.javamaster.javamaster.models.user.User;

@Repository
public class UserDaoImpl extends PaginationDaoImpl<Long, User> implements UserDao  {

    public UserDaoImpl() {
        super();
    }
}
