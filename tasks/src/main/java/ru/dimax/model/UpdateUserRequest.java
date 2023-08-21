package ru.dimax.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class UpdateUserRequest {

    @NotNull
    private Spec spec;

    @NotNull
    private Grade grade;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String name;
}
