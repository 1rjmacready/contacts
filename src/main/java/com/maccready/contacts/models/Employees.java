package com.maccready.contacts.models;

import lombok.Data;

import java.util.List;

@Data
public class Employees {
    private String status;
    private List<Employee> data;
}
