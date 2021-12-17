package ru.javamaster.javamaster.models.directions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.javamaster.javamaster.models.directions.entity.Course;
import ru.javamaster.javamaster.models.directions.entity.DirectionTask;
import ru.javamaster.javamaster.models.directions.entity.InviteToken;
import ru.javamaster.javamaster.models.directions.entity.RecruitmentStudent;
import ru.javamaster.javamaster.models.directions.entity.StudentPreparationInfo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "directions")

public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotEmpty
    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "direction_pic")
    private String directionPic;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "directions_id")
    private List<Course> courses = new ArrayList<>();

    @Column(name = "slack_review_channel")
    private String slackReviewChannel;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "directions_id")
    private List<RecruitmentStudent> recruitmentStudents = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "directions_id")
    private List<StudentPreparationInfo> studentPreparationInfoList = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "directions_id")
    private List<DirectionTask> directionTasks = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "directions_id")
    private Set<InviteToken> inviteTokens = new HashSet<>();

}
