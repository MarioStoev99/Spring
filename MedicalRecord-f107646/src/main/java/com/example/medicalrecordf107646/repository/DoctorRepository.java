package com.example.medicalrecordf107646.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.medicalrecordf107646.model.Doctor;

public interface DoctorRepository extends JpaRepository<Doctor, UUID> {

}
