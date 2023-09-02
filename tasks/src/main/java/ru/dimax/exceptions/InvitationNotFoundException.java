package ru.dimax.exceptions;

public class InvitationNotFoundException extends RuntimeException {
    public InvitationNotFoundException(String message) {
        super(message);
    }
}
