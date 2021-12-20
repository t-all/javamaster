package ru.javamaster.javamaster.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "managers")
public class Manager extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "manager")
    @Column(name = "student_preparation_info")
    private Set<StudentPreparationInfo> studentPreparationInfo = new HashSet<>();


    public Manager(User user){
        super();
    }

}
