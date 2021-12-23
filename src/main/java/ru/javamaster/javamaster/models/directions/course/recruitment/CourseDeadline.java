package ru.javamaster.javamaster.models.directions.course.recruitment;

import javax.persistence.*;

//TODO Класс заглушка для компиляции класса Course
@Entity
@Table(name = "course_deadlines")
public class CourseDeadline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
}
