package ru.javamaster.javamaster.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "studentPreparationInfo")
public class StudentPreparationInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Direction direction;

    private LocalDate startPreparationDate;

    private LocalDate endPreparationDate;

    private Manager Manager;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isEndDirectionCourse = false;

}
