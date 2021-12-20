package ru.javamaster.javamaster.models;

import lombok.*;
import org.hibernate.annotations.Type;
import ru.javamaster.javamaster.models.entity_classes.Direction;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@EqualsAndHashCode(of = {"id", "startPreparationDate", "endPreparationDate"})
@ToString(of = {"id", "startPreparationDate", "endPreparationDate"})
@Table(name = "student_preparation_info")
public class StudentPreparationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_preparation_info_id")
    private Direction direction;

    @Column(name = "start_preparation_date")
    private LocalDate startPreparationDate;

    @Column(name = "end_preparation_date")
    private LocalDate endPreparationDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "manager")
    @JoinColumn(name = "student_preparation_info_id")
    private Manager manager;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "is_end_direction_course")
    private Boolean isEndDirectionCourse = false;

}