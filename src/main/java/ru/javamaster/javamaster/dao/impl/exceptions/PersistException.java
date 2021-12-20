package ru.javamaster.javamaster.dao.impl.exceptions;

public class PersistException extends RuntimeException {

    public PersistException(String message, Throwable cause) {
        super(message, cause);
    }
}
