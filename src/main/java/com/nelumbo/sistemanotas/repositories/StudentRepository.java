package com.nelumbo.sistemanotas.repositories;

import com.nelumbo.sistemanotas.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
}
