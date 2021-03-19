package com.maccready.contacts.controllers;

import com.maccready.contacts.models.Employees;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/wageearners")
public class EmployeeController {
    private final RestTemplate restTemplate;

    private final String employeesURI;

    public EmployeeController(RestTemplate restTemplate, @Value("${employee.api}") String employeeApi) {
        this.restTemplate = restTemplate;
        employeesURI = employeeApi + "/api/v1/employees";
    }

    @GetMapping
    public Employees getEmployees() throws URISyntaxException {
        return restTemplate.getForObject(new URI(employeesURI), Employees.class);
    }
}
