package com.example.hrindex.exception;

import com.example.hrindex.model.Citizen;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CitizenNotFoundException.class)
    public ResponseEntity<CustomErrorResponse> handleCitizenNotFoundException(CitizenNotFoundException ex) {
        String[] citizen = {};
        CustomErrorResponse error = new CustomErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage(), Arrays.asList(citizen));

        return ResponseEntityBuilder.build(error);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<CustomErrorResponse> handleAll(Exception ex, WebRequest request){
        String[] citizen = {};
        CustomErrorResponse error = new CustomErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.getLocalizedMessage(),
                Arrays.asList(citizen)
        );

        return ResponseEntityBuilder.build(error);
    }

}
