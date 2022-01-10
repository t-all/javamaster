package ru.javamaster.javamaster;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.javamaster.javamaster.models.user.User;
import ru.javamaster.javamaster.service.abstr.UserService;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
public class UserTest {

    UserService userService;

    @Autowired
    public UserTest(UserService userService) {
        this.userService = userService;
    }

//    @Transactional
    @Test
    public void addUser() {
        User user1 = new User("bob@test.com", "bob", "bobov", "qwe", LocalDate.of(1987,01,15));
        User user2 = new User("tom@test.com", "tom", "tomov", "qwe", LocalDate.of(2001,05,10));
        User user3 = new User("sam@test.com", "sam", "samov", "qwe", LocalDate.of(1995,11,22));

        userService.addUser(user1);
        userService.addUser(user2);
        userService.addUser(user3);
    }

//    @Transactional
    @Test
    public void getEmailByUserId() {
        addUser();
        Long id = 2L;
        Optional<Object> email = userService.getEmailByUserId(id);
        String result = (String) email.get();
        assertEquals("tom@test.com", result);
        System.out.println("Email with ID: " + id + " = " + result);
    }

//    @Transactional
    //Проверить HQL-запрос, правильный результат на ошибочный email, при проверке в Postgres запрос вреный
    @Test
    public void isExistByEmail() {
        addUser();
        userService.isExistByEmail("bob@test.com");
        System.out.println("test");

    }

//    @Transactional
    @Test
    public void changeEnabledById() {
        addUser();
        Long id = 2L;
        userService.changeEnabledById(id);
    }

//    @Transactional
    @Test
    public void getUserByEmail() {
        addUser();
        String email = "sam@test.com";
        Optional<Object> user = userService.getUserByEmail(email);
        System.out.println("Data for email: " + email + " = " + user);
    }

//    @Transactional
    @Test
    public void getIdByEmail() {
        addUser();
        String email = "bob@test.com";
        Optional<Object> id = userService.getIdByEmail(email);
        Long result = (Long) id.get();
        assertEquals(1L, result);
        System.out.println("ID email: " + email + " = " + result);
    }

}
