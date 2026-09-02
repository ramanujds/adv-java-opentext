package com.helpdeskpro.web.dto;

public record CreateEmployeeRequest(
        String name, String email, boolean agent
) {
}
