package ru.javamaster.javamaster.dao.abstr.model.course;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Course {
    @Id
    private Long id;
}
