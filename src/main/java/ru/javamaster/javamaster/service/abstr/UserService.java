package ru.javamaster.javamaster.service.abstr;

import java.util.Optional;

public interface UserService {

    Optional getEmailByUserId(Long studentId);

    Boolean isExistByEmail(String email);

    void changeEnabledById(Long id);

    Optional getUserByEmail(String email);

    Optional getIdByEmail(String email);
}
