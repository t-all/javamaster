package ru.javamaster.javamaster.models.direction.course.task.theory;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@EqualsAndHashCode(of = {"id", "tetx", "right"})
@ToString(of = {"id", "tetx", "right"})
@Table(name = "probably_answers")

public class ProbablyAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    private String text;

    @NotNull
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean right;

    @ManyToMany(fetch = FetchType.LAZY)
    @Column(name = "theory_task")
    @JoinColumn(name = "probably_answers_id")
    private TheoryTask theoryTask;
}
