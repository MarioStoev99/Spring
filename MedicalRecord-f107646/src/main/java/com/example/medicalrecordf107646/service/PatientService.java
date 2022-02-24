package com.example.medicalrecordf107646.service;

import com.example.medicalrecordf107646.model.Patient;

public interface PatientService {

    Patient create(Patient patient);

    Patient update(String identicalNumber, Patient patient);

    void delete(String identicalNumber);

    Patient getById(String identicalNumber);
}
