package ru.javamaster.javamaster.models.directions.course;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@EqualsAndHashCode(of = {"id","avatarLink","name","email","about"})
@ToString(of = {"id","avatarLink","name","email","about"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "course_authors")
public class CourseAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "avatar_link")
    private String avatarLink;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "about")
    private String about;

}
