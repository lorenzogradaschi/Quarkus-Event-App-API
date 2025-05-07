package it.eventmanager.exception;

public class EventCustomException extends RuntimeException {
    public EventCustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
