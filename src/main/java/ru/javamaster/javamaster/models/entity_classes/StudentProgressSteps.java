package ru.javamaster.javamaster.models.entity_classes;

import javax.persistence.*;

@Entity
@Table(name = "student_progress_steps")
public class StudentProgressSteps {
    //Класс заглушка для компиляции класса Course

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
}
