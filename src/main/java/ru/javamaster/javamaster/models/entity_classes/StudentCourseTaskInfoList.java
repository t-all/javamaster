package ru.javamaster.javamaster.models.entity_classes;

import javax.persistence.*;

@Entity
@Table(name = "student_course_task_info_list")
public class StudentCourseTaskInfoList {
    //Класс заглушка для компиляции класса Course

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;
}
