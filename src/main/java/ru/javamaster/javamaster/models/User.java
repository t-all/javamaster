package ru.javamaster.javamaster.models;

import lombok.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"id", "email"})
@ToString(of = {"id", "email", "firstName", "lastName"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    @Size(min = 1, max = 50, message = "минимальная длина поля email 1 символ, максимальная 60 символов")
    @NonNull
    private String email;

    @Column(name = "first_name")
    @Size(min = 1, message = "минимальная длина 1 символ")
    @NonNull
    private String firstName;

    @Column(name = "last_name")
    @Size(min = 1, message = "минимальная длина 1 символ")
    @NonNull
    private String lastName;

    @Size(min = 6, max = 60, message = "минимальная длина пароля 6 символов, максимальная 60 символов")
    @NonNull
    private String password;

    @Type(type = "org.hibernate.type.LocalDateType")
    @NonNull
    private LocalDate birthday;

    private Boolean enable = true;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = Role.class)
    @JoinColumn(name = "role_id")
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

    @OneToOne(fetch = FetchType.LAZY, targetEntity = Inactivation.class,
            mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    private Inactivation inactivation;

}
