package com.sk.patientservice.repository;

import com.sk.patientservice.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface PatientRepository extends JpaRepository<Patient, UUID> {
    // Checks if any patient exists with the given email
    boolean existsByEmail(String email);
    // Checks if any patient exists with the given email excluding the given patient ID (used during update)
    boolean existsByEmailAndIdNot(String email, UUID id);

}
