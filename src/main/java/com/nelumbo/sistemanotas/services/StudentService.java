package com.nelumbo.sistemanotas.services;

import com.nelumbo.sistemanotas.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    public boolean existsByStudents(){
        return this.studentRepository.count()!=0;
    }
}
