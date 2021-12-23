package ru.javamaster.javamaster.models.directions.course;

import javax.persistence.*;

//TODO Класс заглушка
@Entity
public class CourseTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Chapter.class, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;
}
