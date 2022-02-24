package com.example.medicalrecordf107646.service;

import java.util.List;
import java.util.UUID;

import com.example.medicalrecordf107646.model.MedicalRecord;
import com.example.medicalrecordf107646.model.MedicalRecordResponse;

public interface MedicalRecordService {

    MedicalRecordResponse create(MedicalRecord medicalRecord);

    List<MedicalRecordResponse> get(String patientId);

    List<MedicalRecordResponse> getAll(UUID doctorId);

    List<MedicalRecordResponse> getByDiagnosis(String diagnosis);

    Integer getDoctorVisitorsNumber(UUID doctorId);
}
