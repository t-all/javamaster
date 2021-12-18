package ru.javamaster.javamaster.dao.impl.exceptions;

public class PaginationException extends RuntimeException {

    public PaginationException(String message) {
        super(message);
    }
}
