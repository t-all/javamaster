package ru.javamaster.javamaster.models.directions.course;


import lombok.*;
import org.hibernate.annotations.Type;
import ru.javamaster.javamaster.models.directions.Direction;
import ru.javamaster.javamaster.models.directions.course.recruitment.CourseDeadline;
import ru.javamaster.javamaster.models.user.InviteToken;
import ru.javamaster.javamaster.models.user.StudentProgressStep;


import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@EqualsAndHashCode(of = {"id", "name", "htmlBody"})
@ToString(of = {"id", "name", "htmlBody"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "courses")

public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "html_body")
    private String htmlBody;

    @Column(name = "is_available")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isAvailable = false;

    @Column(name = "is_tutor")
    @Type(type = "org.hibernate.type.NumericBooleanType")
    private Boolean isTutor = false;

    @Column(name = "percent")
    private Integer percent;

    @Column(name = "local_date_time")
    private Integer LocalDateTime;

    @Column(name = "creating_time")
    private Integer creatingTime;


    @Min(value = 1, message = "Minimum value is {value}")
    @Column(name = "position")
    private Integer position;

    @OneToOne
    @JoinColumn(name = "courses_id")
    private CourseAuthor courseAuthor;

    @OneToOne
    @JoinColumn(name = "courses_id")
    private CourseInfo courseInfo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "courses_id")
    private List<ModuleEntity> modules = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "courses_id")
    private List<StudentProgressStep> studentProgressSteps = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "courses_id")
    private List<StudentCourseInfo> studentCourseTaskInfoList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "courses_id")
    private Direction direction;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "courses_id")
    private List<CourseDeadline> courseDeadlines;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "courses_id")
    private Set<InviteToken> inviteTokens;

}
