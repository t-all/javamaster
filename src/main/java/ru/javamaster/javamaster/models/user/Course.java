package ru.javamaster.javamaster.models.user;

import ru.javamaster.javamaster.models.user.CourseTask;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    //Класс заглушка для компиляции класса StudentProgressStep
    @Id
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
    private List<StudentProgressStep> studentProgressSteps = new ArrayList<>();
}
