package com.maccready.contacts.models;

import lombok.Data;

import java.util.Date;
@Data
public class ExceptionResponse {
    private String errors;
    private Date date;

    public ExceptionResponse(String errors, Date date) {
        this.errors = errors;
        this.date = date;
    }
}
