package ru.javamaster.javamaster.models.entity_classes;

import javax.persistence.*;

@Entity
@Table(name = "modules")
public class Module {
    //Класс заглушка для компиляции класса Course

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
}
