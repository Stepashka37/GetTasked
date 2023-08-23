package ru.dimax.exceptions;

public class ConditionViolationException extends RuntimeException {
    public ConditionViolationException(String message) {
        super(message);
    }
}
