package ru.javamaster.javamaster.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
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

    public CourseAuthor(String avatarLink, String name, String email, String about) {
        this.avatarLink = avatarLink;
        this.name = name;
        this.email = email;
        this.about = about;
    }
}
