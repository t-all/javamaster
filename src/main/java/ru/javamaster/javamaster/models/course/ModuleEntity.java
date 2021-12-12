package ru.javamaster.javamaster.models.course;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "modules")
@Getter
@Setter
@NoArgsConstructor
public class ModuleEntity {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;
    @Column
    private String name;
    @Column
    private String description;
    @Column
    private String position;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "module")
    private List<Chapter> chapters;

    @ManyToOne
    @JoinColumn(name = "module_id")
    private Course course;

    public ModuleEntity(String name, String description, String position, List<Chapter> chapters, Course course) {
        this.name = name;
        this.description = description;
        this.position = position;
        this.chapters = chapters;
        this.course = course;
    }
}
