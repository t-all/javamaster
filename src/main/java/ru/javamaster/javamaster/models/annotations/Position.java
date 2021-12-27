package ru.javamaster.javamaster.models.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Position {

    String positionDiscriminant();
}
