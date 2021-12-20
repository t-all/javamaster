package ru.javamaster.javamaster.models.course;
import lombok.*;
import ru.javamaster.javamaster.models.user.CourseTask;
import ru.javamaster.javamaster.models.user.StudentProgressStep;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chapters")
@EqualsAndHashCode (of = {"id", "name", "position"})
@ToString( of = {"id", "name", "position"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Chapter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String name;

    @Column
    private Integer position;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private ModuleEntity module;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chapter")
    private List<CourseTask> courseTasks = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chapter")
    private List<StudentProgressStep> studentProgressSteps = new ArrayList<>();

}
