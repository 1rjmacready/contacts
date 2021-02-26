package com.maccready.contacts.controllers;

import com.maccready.contacts.models.ExceptionResponse;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionHandlerTest {

    @Test
    void handleMethodArgumentNotValid() {
        final Date currentDate = new Date();


        ExceptionHandler exceptionHandler = new ExceptionHandler(){
            @Override
            protected Date getCurrentDate() {
                return currentDate;
            }
        };
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(null, "arbitrary"){
            @Override
            public List<ObjectError> getAllErrors() {
                return Arrays.asList(new ObjectError("arbitrary","First name is invalid"),
                        new ObjectError("arbitrary","Email is invalid"));
            }
        };
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);
        ResponseEntity<Object> result = exceptionHandler.handleMethodArgumentNotValid(exception, null, null, null);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
        ExceptionResponse response = (ExceptionResponse) result.getBody();
        assertSame(currentDate, response.getDate());
        assertEquals("First name is invalid, Email is invalid", response.getErrors());
    }
}