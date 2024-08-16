package com.nelumbo.sistemanotas.services.dto.res;

import lombok.Data;

@Data
public class StudentDtoRes {
    private Long id;
    private String name;
    private Double qualification1;
    private Double qualification2;
    private Double qualification3;
    private Double avgQualification;
}
