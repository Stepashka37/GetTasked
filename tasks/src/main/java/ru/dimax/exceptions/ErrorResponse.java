package ru.dimax.exceptions;

import lombok.Data;

@Data
public class ErrorResponse {
    private final String status;
    private final String reason;
    private final String description;
    private final String timestamp;

}
