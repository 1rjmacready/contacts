package com.maccready.contacts.models;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ExceptionResponse {
    private List<String> errors;
    private Date date;

    public ExceptionResponse(List<String> errors, Date date) {
        this.errors = errors;
        this.date = date;
    }
}
