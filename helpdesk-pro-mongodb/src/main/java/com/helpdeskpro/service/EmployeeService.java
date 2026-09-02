package com.helpdeskpro.service;

import com.helpdeskpro.domain.Employee;
import com.helpdeskpro.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(String name, String email, boolean isAgent) {
        Employee employee = Employee.builder()
                .name(name)
                .email(email)
                .agent(isAgent)
                .build();
        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(String id){
        return employeeRepository.findById(id).orElseThrow(()->new RuntimeException("Employee not found"));
    }

    Employee getEmployeeByEmail(String email){
        return employeeRepository.findByEmail(email).orElseThrow(()->new RuntimeException("Employee not found"));
    }

    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

}
