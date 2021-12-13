package ru.javamaster.javamaster.models.course;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "modules")
@Data
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
    private String position;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "module")
    @JoinColumn(name = "module_id")
    private List<Chapter> chapters;

    @ManyToOne
    private Course course;
}
