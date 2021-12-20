package ru.javamaster.javamaster.dao.impl.exceptions;

public class RemoveException extends RuntimeException {

    public RemoveException(String message) {
        super(message);
    }

    public RemoveException(String message, Throwable cause) {
        super(message, cause);
    }
}
