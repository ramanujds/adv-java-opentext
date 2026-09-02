package com.helpdeskpro.web;

import com.helpdeskpro.service.EmployeeService;
import com.helpdeskpro.web.dto.CreateEmployeeRequest;
import com.helpdeskpro.web.dto.EmployeeResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {


    private final EmployeeService employeeService;


    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public EmployeeResponse createEmployee(@RequestBody @Valid CreateEmployeeRequest employeeRequest){
        var employee = employeeService.createEmployee(employeeRequest.name(), employeeRequest.email(), employeeRequest.agent());
        return EmployeeResponse.from(employee);
    }

    @GetMapping
    public List<EmployeeResponse> getAllEmployees(){
        return employeeService.getAllEmployees().stream().map(EmployeeResponse::from).toList();
    }

    @GetMapping("/{id}")
    public EmployeeResponse getEmployeeById(@PathVariable UUID id){
        return EmployeeResponse.from(employeeService.getEmployeeById(id));
    }
}
