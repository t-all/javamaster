package ru.javamaster.javamaster.models;

import lombok.Data;

@Data
public class CourseInfo {

    private String description;
    private int filling;
    private String transitTime;
    private String demands;
    private int target;
    private String coursePicUrl;
}
