package ru.javamaster.javamaster.models.directions.tasks;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

import ru.javamaster.javamaster.models.directions.Direction;
import ru.javamaster.javamaster.models.directions.tasks.comment.DirectionTaskComment;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import javax.validation.constraints.Min;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = {"id", "position", "task"})
@ToString(of = {"id", "position", "task"})
@Table(name = "direction_tasks")
public class DirectionTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "position")
    @Min(value = 1, message = "Minimum value is {value}")
    @NonNull
    private Integer position;

    @OneToOne(fetch = FetchType.LAZY, targetEntity = Task.class, cascade = CascadeType.REFRESH)
    @JoinColumn(name= "task_id")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "direction_id")
    private Direction direction;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy= "directionTask")
    private Set<StudentDirectionTask> studentDirectionTasks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name= "direction_task_comment_id")
    private Set<DirectionTaskComment> directionTaskComments = new HashSet<>();
}
