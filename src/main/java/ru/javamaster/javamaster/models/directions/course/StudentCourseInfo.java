package ru.javamaster.javamaster.models.directions.course;

import javax.persistence.*;

//TODO Класс заглушка для компиляции класса Course
@Entity
@Table(name = "student_course_task_info")
public class StudentCourseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
}
