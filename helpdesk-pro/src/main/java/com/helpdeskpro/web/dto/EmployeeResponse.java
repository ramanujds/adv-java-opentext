package com.helpdeskpro.web.dto;

import com.helpdeskpro.domain.Employee;

import java.util.UUID;

public record EmployeeResponse(UUID id, String name, String email, boolean agent) {

    public static EmployeeResponse from(Employee employee) {
        return new EmployeeResponse(employee.getId(), employee.getName(), employee.getEmail(), employee.isAgent());
    }

}
