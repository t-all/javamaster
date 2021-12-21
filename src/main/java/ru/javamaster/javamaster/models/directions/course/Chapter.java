package ru.javamaster.javamaster.models.directions.course;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.javamaster.javamaster.dao.abstr.model.course.ModuleEntity;
import ru.javamaster.javamaster.models.user.StudentProgressStep;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chapters")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "name", "position"})
@ToString( of = {"id", "name", "position"})
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
