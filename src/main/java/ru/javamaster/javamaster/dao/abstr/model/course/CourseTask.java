package ru.javamaster.javamaster.dao.abstr.model.course;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CourseTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
