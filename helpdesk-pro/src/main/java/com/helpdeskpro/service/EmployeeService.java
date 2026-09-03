package com.helpdeskpro.service;

import com.helpdeskpro.domain.Employee;
import com.helpdeskpro.repository.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;


    public EmployeeService(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Employee createEmployee(String name, String email, boolean isAgent, String password) {
        Employee employee = Employee.builder()
                .name(name)
                .email(email)
                .agent(isAgent)
                .password(passwordEncoder.encode(password))
                .build();
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(UUID id){
        return employeeRepository.findById(id).orElseThrow(()->new RuntimeException("Employee not found"));
    }

    Employee getEmployeeByEmail(String email){
        return employeeRepository.findByEmail(email).orElseThrow(()->new RuntimeException("Employee not found"));
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

}
