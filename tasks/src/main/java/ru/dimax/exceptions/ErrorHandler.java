package ru.dimax.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    public static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    @ExceptionHandler({UserNotFoundException.class, TaskNotFoundException.class, InvitationNotFoundException.class, RequestNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundException(final RuntimeException exception) {
        log.error("404: " + exception.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.name(),
                "The required object was not found.",
                exception.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
    }

    @ExceptionHandler(ConditionViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse condViolationExc(final RuntimeException exception) {
        log.error("400: " + exception.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.name(),
                "The condition has been violated",
                exception.getMessage(),
                LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
    }



}
