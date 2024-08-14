package com.nelumbo.sistemanotas.repositories;

import com.nelumbo.sistemanotas.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject,Long> {
}
