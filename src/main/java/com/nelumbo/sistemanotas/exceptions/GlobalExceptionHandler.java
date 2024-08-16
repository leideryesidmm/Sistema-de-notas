package com.nelumbo.sistemanotas.exceptions;

import com.nelumbo.sistemanotas.services.dto.res.MessageDtoRes;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageDtoRes handleIllegalArgumentException(IllegalArgumentException e) {
        return new MessageDtoRes(e.getMessage());
    }
    @ExceptionHandler(FileException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageDtoRes handleFileException(FileException e) {
        return new MessageDtoRes(e.getMessage());
    }
    @ExceptionHandler(FileFormatException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageDtoRes handleFileFormatException(FileFormatException e) {
        return new MessageDtoRes(e.getMessage());
    }
    @ExceptionHandler(DatosInvalidosException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MessageDtoRes handleDatosInvalidosException(DatosInvalidosException e) {
        return new MessageDtoRes(e.getMessage());
    }

}