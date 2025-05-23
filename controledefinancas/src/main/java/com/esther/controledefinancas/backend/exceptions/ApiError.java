package com.esther.controledefinancas.backend.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
class ApiError {
    private HttpStatus status;
    private String message;

    public ApiError(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

}



