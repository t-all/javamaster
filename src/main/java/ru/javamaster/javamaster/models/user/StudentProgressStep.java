package ru.javamaster.javamaster.models.user;

import lombok.*;
import ru.javamaster.javamaster.models.course.Chapter;
import ru.javamaster.javamaster.models.course.ModuleEntity;

import javax.persistence.*;

@Entity
@Table(name = "student_progress_steps")
@EqualsAndHashCode (of = {"id", "name", "description", "position"})
@ToString( of = {"id", "name", "description", "position"})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentProgressStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NonNull
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "course_id")
    Course course;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "module_id")
    ModuleEntity module;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "chapter_id")
    Chapter chapter;
}
