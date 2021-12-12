package ru.javamaster.javamaster.models;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "html_body")
    private String htmlBody;

    @Column(name = "is_available")
    private boolean isAvailable = false;

    @Column(name = "is_tutor")
    private boolean isTutor = false;

    @Column(name = "percent")
    private int percent;

    @Column(name = "local_date_time")

    private int LocalDateTime;

    @Column(name = "creating_time")
    private int creatingTime;


    @Min(1)
    @Column(name = "position")
    private int position;

    public Course(String name, String htmlBody, boolean isAvailable, boolean isTutor, int percent, int localDateTime, int creatingTime, int position) {
        this.name = name;
        this.htmlBody = htmlBody;
        this.isAvailable = isAvailable;
        this.isTutor = isTutor;
        this.percent = percent;
        LocalDateTime = localDateTime;
        this.creatingTime = creatingTime;
        this.position = position;
    }

    @OneToMany
    @JoinColumn(name = "courses_id")
    private CourseAuthor courseAuthor;

    @OneToMany
    @JoinColumn(name = "courses_id")
    private CourseInfo courseInfo;

    @OneToMany
    @JoinColumn(name = "courses_id")
    private List<Modules> modules;

    @OneToMany
    @JoinColumn(name = "courses_id")
    private List<StudentProgressSteps> studentProgressSteps;

    @OneToMany
    @JoinColumn(name = "courses_id")
    private List<StudentCourseTaskInfoList> studentCourseTaskInfoList;

    @ManyToOne
    @JoinColumn(name = "courses_id")
    private Direction direction;

    @OneToMany
    @JoinColumn(name = "courses_id")
    private List<CourseDeadlines> courseDeadlines;

    @OneToMany
    @JoinColumn(name = "courses_id")
    Set<InviteTokens> inviteTokens;

}
