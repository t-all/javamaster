package ru.javamaster.javamaster.models;


import lombok.Data;

import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Set;

@Data
public class Course {

    private String name;
    private String htmlBody;
    private boolean isAvailable = false;
    private boolean isTutor = false;
    private int percent;
    private int LocalDateTime;
    private int creatingTime;

    @Min(1)
    private int position;

    @OneToMany
    private CourseAuthor courseAuthor;

    @OneToMany
    private CourseInfo courseInfo;

    @OneToMany
    private List modules;

    @OneToMany
    private List studentProgressSteps;

    @OneToMany
    private List studentCourseTaskInfoList;

    @ManyToOne
    private Direction direction;

    @OneToMany
    private List courseDeadlines;

    @OneToMany
    Set inviteTokens;

}
