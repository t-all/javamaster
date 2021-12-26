package ru.javamaster.javamaster.models.directions.tasks.theory;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@EqualsAndHashCode(of = {"id", "text", "right"})
@ToString(of = {"id", "text", "right"})
@Table(name = "probably_answers")

public class ProbablyAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String text;

    @NotNull
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private boolean right;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "theory_task_id")
    private TheoryTask theoryTask;
}
