package com.nelumbo.sistemanotas.services;

import com.nelumbo.sistemanotas.entities.Student;
import com.nelumbo.sistemanotas.exceptions.ResourceNotFoundException;
import com.nelumbo.sistemanotas.repositories.StudentRepository;
import com.nelumbo.sistemanotas.services.dto.res.StudentDtoRes;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final ModelMapper modelMapper;
    public boolean existsByStudents(){
        return this.studentRepository.count()!=0;
    }
    public StudentDtoRes getStudentById(Long id){
        Student subject=this.studentRepository.findById(id).orElseThrow(
                ()->
                        new ResourceNotFoundException("El estudiante con el id "+id+" no existe")
        );
        return modelMapper.map(subject,StudentDtoRes.class);
    }
}
