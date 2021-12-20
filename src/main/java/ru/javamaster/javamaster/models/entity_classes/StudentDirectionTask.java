package ru.javamaster.javamaster.models.entity_classes;

import ru.javamaster.javamaster.models.Student;

import javax.persistence.*;

@Entity
@Table(name = "student_direction_task")
public class StudentDirectionTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "students_id")
    private Student student;
}
