package ru.javamaster.javamaster.models.directions.task;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
//import org.springframework.scheduling.config.Task;
import ru.javamaster.javamaster.models.directions.Direction;
import ru.javamaster.javamaster.models.directions.course.task.comment.DirectionTaskComment;

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
@Table(name = "direction_tasks")

public class DirectionTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "position")
    @Min(value = 0, message = "Minimum value is {value}")
    @NonNull
    private Integer position;

    @OneToOne(fetch = FetchType.LAZY,
            targetEntity = Task.class,
            cascade = CascadeType.REFRESH,
            mappedBy = "direction_task")
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = CascadeType.REFRESH)
    private Direction direction;

    @OneToMany(fetch = FetchType.LAZY,
                cascade = CascadeType.REMOVE)
    private Set<StudentDirectionTask> studentDirectionTasks = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.REMOVE)
    private Set<DirectionTaskComment> directionTaskComments = new HashSet<>();
}
