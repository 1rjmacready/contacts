package com.maccready.contacts.controllers;

import com.maccready.contacts.models.ExceptionResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionHandlerTest {

    private Date currentDate;
    private ExceptionHandler exceptionHandler;

    @BeforeEach
    void setUp() {
        currentDate = new Date();
        exceptionHandler = new ExceptionHandler(){
            @Override
            protected Date getCurrentDate() {
                return currentDate;
            }
        };
    }

    @Test
    void handleMethodArgumentNotValid() {

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
        assertSame(currentDate, Objects.requireNonNull(response).getDate());
        assertArrayEquals(new String[]{"First name is invalid", "Email is invalid"}, response.getErrors().toArray());
    }

    @Test
    void handleAllExceptions() {
        ResponseEntity<ExceptionResponse> result =
                exceptionHandler.handleAllExceptions(new RuntimeException("Epic Fail"));
        ExceptionResponse body = result.getBody();
        assertArrayEquals(new String[]{"Epic Fail"}, Objects.requireNonNull(body).getErrors().toArray());
        assertSame(currentDate,body.getDate());
    }
}