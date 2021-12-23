package ru.javamaster.javamaster.models.directions.course;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modules")
@EqualsAndHashCode(of = {"id", "name", "description", "position"})
@ToString( of = {"id", "name", "description", "position"})
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
    //done
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "module")
    private List<Chapter> chapters = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

}
