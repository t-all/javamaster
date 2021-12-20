package ru.javamaster.javamaster.models.direction.task;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@EqualsAndHashCode(of = {"id", "type", "title", "description"})
@ToString(of = {"id", "type", "title", "description"})
@Table(name = "tasks")
@Inheritance(strategy = InheritanceType.JOINED)
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",
            nullable = false)
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private TaskType type;

    @Column(name = "title")
    @NotEmpty(message = "Field Title can't be empty !")
    private String title;

    @Column(name = "description")
    @NotEmpty(message = "Field Description can't be empty !")
    private String description;

    @Column(name = "points")
    @Min(value = 0, message = "Minimum value is {value}")
    private Integer points;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "is_available")
    private Boolean isAvailable = true;

    @OneToOne(fetch = FetchType.LAZY,
              targetEntity = DirectionTask.class,
              cascade = CascadeType.ALL,
              mappedBy = "task")
    private DirectionTask directionTask;
}
