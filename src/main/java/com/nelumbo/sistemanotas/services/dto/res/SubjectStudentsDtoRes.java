package com.nelumbo.sistemanotas.services.dto.res;

import lombok.Data;

import java.util.List;

@Data
public class SubjectStudentsDtoRes {
    private SubjectDtoRes subject;
    private List<StudentDtoRes> students;
}
