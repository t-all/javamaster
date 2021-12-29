package ru.javamaster.javamaster.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import ru.javamaster.javamaster.dao.abstr.user.UserDao;
import ru.javamaster.javamaster.service.abstr.UserService;

import java.util.Optional;

public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional getEmailByUserId(Long studentId) {
        return (Optional) userDao.getEmailByUserId(studentId);
    }

    @Override
    public Boolean isExistByEmail(String email) {
        return userDao.isExistByEmail(email);
    }

    @Override
    public void changeEnabledById(Long id) {
        userDao.changeEnabledById(id);
    }

    @Override
    public Optional getUserByEmail(String email) {
        return (Optional) userDao.getUserByEmail(email);
    }

    @Override
    public Optional getIdByEmail(String email) {
        return (Optional) userDao.getIdByEmail(email);
    }
}
