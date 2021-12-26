package ru.javamaster.javamaster.models.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

//TODO заглушка для класса InviteToken

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "tutors")
public class Tutor {

    @Id
    @Column(name = "id")
    Long id;

}
