package com.example.medicalrecordf107646.service;

import java.util.List;
import java.util.UUID;

import com.example.medicalrecordf107646.model.Doctor;
import com.example.medicalrecordf107646.model.Patient;

public interface DoctorService {

    Doctor create(Doctor doctor);

    Doctor update(UUID doctorId, Doctor doctor);

    void delete(UUID doctorId);

    Doctor getById(UUID id);

    List<Doctor> getAllById(List<UUID> ids);

    Doctor  addPatient(UUID doctorId, Patient patient);
}
