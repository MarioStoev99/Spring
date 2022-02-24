package com.example.medicalrecordf107646.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.medicalrecordf107646.model.Patient;

public interface PatientRepository extends JpaRepository<Patient, String> {

}
