package ru.javamaster.javamaster.dao.abstr.model.course;

import javax.persistence.*;

@Entity
public class CourseTask {
    //Класс заглушка для компиляции класса Chapter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;
}
