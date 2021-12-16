package ru.javamaster.javamaster.models.entity_classes;

import javax.persistence.*;

@Entity
@Table(name = "directions")
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
}
