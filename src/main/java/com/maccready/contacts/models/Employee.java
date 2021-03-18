package com.maccready.contacts.models;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

@SuppressWarnings("unused")
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Employee {
    private long id;
    private String employeeName;
    private String employeeSalary;
    private int employeeAge;
    private String profileImage;
}
