package ru.javamaster.javamaster.models.directions.course;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.javamaster.javamaster.models.directions.tasks.comment.CourseTaskComment;
import ru.javamaster.javamaster.models.user.student.StudentCourseTaskInfoList;
import ru.javamaster.javamaster.models.directions.tasks.Task;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@EqualsAndHashCode(of = {"id", "task", "position", "chapter"})
@ToString( of = {"id", "task", "position", "chapter"})
@Table(name = "course_tasks")
public class CourseTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_id")
    private Task task;

    @NotNull
    @Min(value = 1, message = "Minimal value is + {value}")
    private Integer position;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "task_comment_id")
    private List<CourseTaskComment> taskComments = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_tasks_id")
    private List<StudentCourseTaskInfoList> studentCourseTaskInfoList = new ArrayList<>();
    
}
