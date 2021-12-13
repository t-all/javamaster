package ru.javamaster.javamaster.models.course;
import lombok.*;
import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "chapters")
@Data
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
    private String position;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private ModuleEntity module;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chapter")
    private List<CourseTask> courseTasks = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chapter")
    private List<StudentProgressStep> studentProgressSteps = new ArrayList<>();

}
