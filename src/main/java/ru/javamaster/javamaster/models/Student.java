package ru.javamaster.javamaster.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.javamaster.javamaster.models.entity_classes.StudentDirectionTask;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "students")
public class Student extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_preparation_info_id")
    private StudentPreparationInfo studentPreparationInfo;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<StudentDirectionTask> studentDirectionTask = new HashSet<>();

}
