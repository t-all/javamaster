package ru.javamaster.javamaster.models.course;
import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "chapters")
@Getter
@Setter
@NoArgsConstructor
public class Chapter {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column
    private String name;
    @Column
    private String position;

    @ManyToOne
    @JoinColumn(name = "modules_id")
    private ModuleEntity module;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chapter")
    private List<CourseTask> courseTasks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chapter")
    private List<StudentProgressStep> studentProgressSteps;

    public Chapter(String name, String position, ModuleEntity module, List<CourseTask> courseTasks, List<StudentProgressStep> studentProgressSteps) {
        this.name = name;
        this.position = position;
        this.module = module;
        this.courseTasks = courseTasks;
        this.studentProgressSteps = studentProgressSteps;
    }
}
