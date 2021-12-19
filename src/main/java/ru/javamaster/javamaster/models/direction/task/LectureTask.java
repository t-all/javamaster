package ru.javamaster.javamaster.models.direction.task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lecture_task")
public class LectureTask extends Task{

    public LectureTask(Task task) {
        super();
    }
}
