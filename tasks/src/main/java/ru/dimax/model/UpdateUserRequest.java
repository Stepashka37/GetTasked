package ru.dimax.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class UpdateUserRequest {

    @NotBlank
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotNull
    private Grade grade;

    @NotNull
    private Spec spec;

}
