package ru.javamaster.javamaster.models;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@RequiredArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(name = "email", unique = true, nullable = false)
    /**
     * минимальная длина поля email 1 символ, максимальная 60 символов
     */
    @Size(min = 1, max = 50)
    @NonNull
    private String email;

    @Column(name = "first_name", nullable = false)
    @NonNull
    /**
     * минимальная длина поля firstName 1 символ
     */
    @Size(min = 1)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    /**
     * минимальная длина поля lastName 1 символ
     */
    @Size(min = 1)
    @NonNull
    private String lastName;

    @Column(nullable = false)
    /**
     * минимальная длина поля password 6 символов, максимальная 60 символов
     */
    @Size(min = 6, max = 60)
    @NonNull
    private String password;

    @Column(nullable = false)
    @Type(type = "org.hibernate.type.LocalDateType")
    @NonNull
    private LocalDate birthday;

    private Boolean enable = true;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Role.class)
    @JoinColumn(name = "role_id", nullable = false)
    @NonNull
    private Role role;

    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime registrationDate;

    @Column(name = "last_visit")
    @Type(type = "org.hibernate.type.LocalDateTimeType")
    private LocalDateTime lastVisitDate;

    @Column(name = "registration_source")
    private String registrationSource;

    @Column(name = "local_tag")
    private String localeTag = "ru";

    private Boolean avatarIsExist = false;

    @OneToOne
    private Inactivation inactivation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId().equals(user.getId()) && getEmail().equals(user.getEmail());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail());
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
