package ru.javamaster.javamaster.models.user;

import lombok.*;
import org.springframework.lang.Nullable;
import ru.javamaster.javamaster.models.course.Chapter;
import ru.javamaster.javamaster.models.course.ModuleEntity;

import javax.persistence.*;

@Entity
@Table(name = "studentProgressSteps")
@NonNull

@NoArgsConstructor
@AllArgsConstructor
public class StudentProgressStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    Course course;

    @ManyToOnen
    @JoinColumn(name = "module_id")
    ModuleEntity module;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    Chapter chapter;
}
