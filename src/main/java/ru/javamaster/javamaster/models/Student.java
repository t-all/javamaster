package ru.javamaster.javamaster.models;

import lombok.*;
import ru.javamaster.javamaster.models.entity_classes.StudentDirectionTask;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@DiscriminatorValue(value = "Student")
@ToString(callSuper = true, exclude = {"studentPreparationInfo", "studentDirectionTask"})
@EqualsAndHashCode(callSuper = true, exclude = {"studentPreparationInfo", "studentDirectionTask"})
public class Student extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "student_preparation_info_id", unique = true)
    private StudentPreparationInfo studentPreparationInfo;

    @OneToMany(mappedBy = "student", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private Set<StudentDirectionTask> studentDirectionTask = new HashSet<>();

}
