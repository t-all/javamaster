package ru.javamaster.javamaster.dao.abstr.model.course;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "chapters")
@EqualsAndHashCode(of = {"id", "name", "position"})
@ToString( of = {"id", "name", "position"})
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
