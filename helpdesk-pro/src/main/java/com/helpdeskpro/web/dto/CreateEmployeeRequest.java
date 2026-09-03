package com.helpdeskpro.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateEmployeeRequest(
        @NotBlank String name, @NotBlank @Email String email, boolean agent, String password
) {
}
