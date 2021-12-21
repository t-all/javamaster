package ru.javamaster.javamaster.models.directions.course;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@EqualsAndHashCode(of = {"id", "description", "filling", "transitTime", "demands"})
@ToString(of = {"id", "description", "filling", "transitTime", "demands"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course_infos")
public class CourseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @Lob
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "filling")
    private Integer filling;

    @Lob
    @Column(name = "transit_time")
    private String transitTime;

    @Column(name = "demands")
    private String demands;

    @Column(name = "target")
    private Integer target;

    @Column(name = "course_pic_url")
    private String coursePicUrl;

}
