package ru.javamaster.javamaster.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.javamaster.javamaster.dao.abstr.user.UserDao;
import ru.javamaster.javamaster.models.user.User;
import ru.javamaster.javamaster.service.abstr.UserService;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<Object> getEmailByUserId(Long studentId) {
        return userDao.getEmailByUserId(studentId);
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
    public Optional<Object> getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public Optional<Object> getIdByEmail(String email) {
        return userDao.getIdByEmail(email);
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }
}
