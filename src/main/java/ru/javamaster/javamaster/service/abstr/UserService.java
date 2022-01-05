package ru.javamaster.javamaster.service.abstr;

import ru.javamaster.javamaster.models.user.User;

import java.util.Optional;

public interface UserService {

    Optional<Object> getEmailByUserId(Long studentId);

    Boolean isExistByEmail(String email);

    void changeEnabledById(Long id);

    Optional<Object> getUserByEmail(String email);

    Optional<Object> getIdByEmail(String email);

    void addUser(User user);
}
