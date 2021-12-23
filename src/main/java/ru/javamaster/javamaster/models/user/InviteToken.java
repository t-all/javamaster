package ru.javamaster.javamaster.models.user;

import lombok.*;
import ru.javamaster.javamaster.models.directions.Direction;
import ru.javamaster.javamaster.models.directions.course.Course;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id", "hash", "curatorEmail"})
@ToString(of = {"id", "hash", "curatorEmail"})
@Entity
@Table(name = "invite_tokens")
public class InviteToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @NotNull
    @Column(name = "hash", unique = true)
    private String hash;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "direction_id")
    Direction direction;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "tutor_id")
    Tutor tutor = new Tutor(RoleNameEnum.TUTOR);

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "course_id")
    private Course course;

    @Column(name = "curator_email")
    private String curatorEmail;
}
