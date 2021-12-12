package ru.javamaster.javamaster.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "course_infos")
public class CourseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "description")
    private String description;

    @Column(name = "filling")
    private int filling;

    @Column(name = "transit_time")
    private String transitTime;

    @Column(name = "demands")
    private String demands;

    @Column(name = "target")
    private int target;

    @Column(name = "course_pic_url")
    private String coursePicUrl;

    public CourseInfo(String description, int filling, String transitTime, String demands, int target, String coursePicUrl) {
        this.description = description;
        this.filling = filling;
        this.transitTime = transitTime;
        this.demands = demands;
        this.target = target;
        this.coursePicUrl = coursePicUrl;
    }
}
