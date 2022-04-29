package com.example.hrindex.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityBuilder {
    public static ResponseEntity<CustomErrorResponse> build(CustomErrorResponse customErrorResponse) {
        return new ResponseEntity<CustomErrorResponse>(customErrorResponse, HttpStatus.valueOf(customErrorResponse.getStatus()));
    }
}
