package com.example.testvideosystem.controllers;

import com.example.testvideosystem.data.responses.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExceptionController {

    @ExceptionHandler(InvalidFormatException.class)
    @ResponseBody
    public ErrorResponse handleInvalidFormatException(InvalidFormatException e) {
        return new ErrorResponse(e.getMessage());
    }
}
