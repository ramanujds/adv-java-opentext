package com.helpdeskpro.security;

import com.helpdeskpro.domain.Employee;
import com.helpdeskpro.repository.EmployeeRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserService {

    private EmployeeRepository employeeRepository;
    public CurrentUserService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee getCurrentUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return employeeRepository.findByEmail(email).orElseThrow(()->new RuntimeException("Employee not found"));
    }

}
