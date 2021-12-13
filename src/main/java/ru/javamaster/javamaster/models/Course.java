package ru.javamaster.javamaster.models;


import lombok.*;
import org.hibernate.annotations.Type;


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
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "html_body")
    private String htmlBody;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "is_available")
    private Boolean isAvailable = false;

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "is_tutor")
    private Boolean isTutor = false;

    @Column(name = "percent")
    private Integer percent;

    @Column(name = "local_date_time")
    private Integer LocalDateTime;

    @Column(name = "creating_time")
    private Integer creatingTime;


    @Min(1)
    @Column(name = "position")
    private Integer position;

    @OneToOne
    @JoinColumn(name = "courses_id")
    private CourseAuthor courseAuthor;

    @OneToOne
    @JoinColumn(name = "courses_id")
    private CourseInfo courseInfo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "courses_id")
    private List<Modules> modules = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "courses_id")
    private List<StudentProgressSteps> studentProgressSteps = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "courses_id")
    private List<StudentCourseTaskInfoList> studentCourseTaskInfoList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "courses_id")
    private Direction direction;

    @OneToMany
    @JoinColumn(name = "courses_id")
    private List<CourseDeadlines> courseDeadlines;

    @OneToMany
    @JoinColumn(name = "courses_id")
    Set<InviteTokens> inviteTokens;

}
