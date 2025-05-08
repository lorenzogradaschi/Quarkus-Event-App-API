package it.eventmanager.exception;

public class UserCustomException extends RuntimeException {
    public UserCustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
