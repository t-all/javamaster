package ru.javamaster.javamaster.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.apachecommons.CommonsLog;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "students_preparation_info")
public class StudentPreparationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "students_preparation_info_id")
    private Direction direction;

    @Column(name = "start_preparation_date")
    private LocalDate startPreparationDate;

    @Column(name = "end_preparation_date")
    private LocalDate endPreparationDate;

    @Column(name = "manager")
    private Manager manager;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "is_end_direction_course")
    private Boolean isEndDirectionCourse = false;

}
