package ru.javamaster.javamaster.models.user;

import javax.persistence.*;

//TODO Класс заглушка для компиляции класса Course
@Entity
@Table(name = "invite_tokens")
public class InviteToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
}
