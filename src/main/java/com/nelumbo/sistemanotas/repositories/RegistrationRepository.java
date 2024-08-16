package com.nelumbo.sistemanotas.repositories;

import com.nelumbo.sistemanotas.entities.Registration;
import com.nelumbo.sistemanotas.entities.Student;
import com.nelumbo.sistemanotas.entities.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RegistrationRepository extends JpaRepository<Registration,Long> {
     List<Registration> findAllBySubject(Subject subject);
     Optional<Registration> findByStudentAndSubject(Student student, Subject subject);
     @Modifying
     @Transactional
     @Query("UPDATE Registration r SET r.qualification1=:qualification1, r.qualification2=:qualification2, r.qualification3=:qualification3 WHERE r.id=:id")
     void editQualifications(@Param("id") Long id,
                             @Param("qualification1") Double qualification1,
                             @Param("qualification2") Double qualification2,
                             @Param("qualification3") Double qualification3);
     @Query("SELECT r FROM Registration r WHERE r.subject=:subject ORDER BY (r.qualification1 + r.qualification2 + r.qualification3) DESC")
     List<Registration> findAllOrderedByTotalQualifications(@Param("subject") Subject subject);
}