package ru.javamaster.javamaster.models;

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
    private Long id;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "filling")
    private Integer filling;

    @Column(name = "transit_time")
    private String transitTime;

    @Lob
    @Column(name = "demands")
    private String demands;

    @Lob
    @Column(name = "target")
    private Integer target;

    @Column(name = "course_pic_url")
    private String coursePicUrl;

}
