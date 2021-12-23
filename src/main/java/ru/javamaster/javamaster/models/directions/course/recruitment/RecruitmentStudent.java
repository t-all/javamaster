package ru.javamaster.javamaster.models.directions.course.recruitment;

import javax.persistence.*;

//TODO Класс заглушка для компиляции класса Course
@Entity
@Table(name ="recruitment_students")
public class RecruitmentStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
