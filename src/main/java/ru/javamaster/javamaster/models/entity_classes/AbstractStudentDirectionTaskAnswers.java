package ru.javamaster.javamaster.models.entity_classes;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "abstract_student_direction_task_answers")
public class AbstractStudentDirectionTaskAnswers {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_direction_task_id")
    private StudentDirectionTask studentDirectionTask;

    public StudentDirectionTask getStudentDirectionTask() {
        return studentDirectionTask;
    }

    public void setStudentDirectionTask(StudentDirectionTask studentDirectionTask) {
        this.studentDirectionTask = studentDirectionTask;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}