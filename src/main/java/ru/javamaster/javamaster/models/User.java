package ru.javamaster.javamaster.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email", unique = true, length = 50)
    private String email;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;

    @Size(min = 6, max = 60)
    private String password;
    private LocalDate birthday;
    private boolean enable = true;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
    private LocalDateTime registrationDate;

    @Column(name = "last_visit")
    private LocalDateTime lastVisitDate;
    @Column(name = "registration_source")
    private String registrationSource;
    @Column(name = "local_tag")
    private String localeTag = "ru";
    private boolean avatarIsExist = false;


    @SuppressWarnings("JpaAttributeTypeInspection")
    private Inactivation inactivation;

    public User(String email, String firstName, String lastName, String password, LocalDate birthday, Role role) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.birthday = birthday;
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", birthday=" + birthday +
                ", enable=" + enable +
                ", role=" + role +
                ", registrationDate=" + registrationDate +
                ", lastVisitDate=" + lastVisitDate +
                ", registrationSource='" + registrationSource + '\'' +
                ", localeTag='" + localeTag + '\'' +
                ", avatarIsExist=" + avatarIsExist +
                ", inactivation=" + inactivation +
                '}';
    }
}
