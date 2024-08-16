package com.nelumbo.sistemanotas.services.dto.res;

import lombok.Data;

@Data
public class RegistrationDtoRes {
    private Long id;
    private StudentDtoRes student;
    private SubjectDtoRes subject;
    private  Double qualification1;
    private  Double qualification2;
    private  Double qualification3;

}
