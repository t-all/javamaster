package ru.javamaster.javamaster.models.directions.tasks;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "word_answer_task")
public class WordAnswerTask extends Task{

    @Column(name = "answer")
    private String answer;

    public WordAnswerTask(Task task) {
        super();
        task.setType(TaskType.word);
    }
}
