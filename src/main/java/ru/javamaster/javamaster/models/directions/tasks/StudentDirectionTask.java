package ru.javamaster.javamaster.models.directions.tasks;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "student_direction_task")
public class StudentDirectionTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY,
                cascade = CascadeType.REFRESH)
    @JoinColumn(name = "direction_task_id")
    private DirectionTask directionTask;

    public DirectionTask getDirectionTask() {
        return directionTask;
    }

    public void setDirectionTask(DirectionTask directionTask) {
        this.directionTask = directionTask;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}