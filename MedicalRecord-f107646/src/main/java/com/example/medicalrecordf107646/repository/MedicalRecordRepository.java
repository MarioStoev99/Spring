package com.example.medicalrecordf107646.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.medicalrecordf107646.model.MedicalRecord;

public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, UUID> {

    List<MedicalRecord> findAllByPatientId(String patientId);

    List<MedicalRecord> findAllByDiagnosis(String diagnosis);

}
