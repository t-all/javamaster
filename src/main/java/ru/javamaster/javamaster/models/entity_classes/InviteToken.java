package ru.javamaster.javamaster.models.entity_classes;

import javax.persistence.*;

@Entity
@Table(name = "invite_tokens")
public class InviteToken {
    //Класс заглушка для компиляции класса Course

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
}
