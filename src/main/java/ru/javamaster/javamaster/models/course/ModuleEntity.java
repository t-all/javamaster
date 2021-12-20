package ru.javamaster.javamaster.models.course;

import lombok.*;
import ru.javamaster.javamaster.models.user.Course;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modules")
@EqualsAndHashCode (of = {"id", "name", "description", "position"})
@ToString( of = {"id", "name", "description", "position"})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Integer position;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "module")
    @JoinColumn(name = "course_id")
    private List<Chapter> chapters = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
}
