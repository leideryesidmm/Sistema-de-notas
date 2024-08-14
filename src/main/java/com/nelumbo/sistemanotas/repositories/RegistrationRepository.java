package com.nelumbo.sistemanotas.repositories;

import com.nelumbo.sistemanotas.entities.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository<Registration,Long> {
}
