package ru.javamaster.javamaster.models.directions.tasks;

import javax.persistence.*;

//TODO Класс заглушка для компиляции класса Course
@Entity
@Table(name ="direction_tasks")
public class DirectionTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    private Task task;
}
