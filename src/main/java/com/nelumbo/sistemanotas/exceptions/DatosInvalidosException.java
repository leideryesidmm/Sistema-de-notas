package com.nelumbo.sistemanotas.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class DatosInvalidosException extends RuntimeException{
    public DatosInvalidosException(String message) {
        super(message);
    }
}
