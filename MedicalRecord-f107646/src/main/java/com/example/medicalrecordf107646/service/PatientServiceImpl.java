package com.example.medicalrecordf107646.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.medicalrecordf107646.contants.ErrorMessageTemplate;
import com.example.medicalrecordf107646.model.Patient;
import com.example.medicalrecordf107646.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;

    @Override
    public Patient create(Patient patient) {
        String identicalNumber = patient.getIdenticalNumber();
        Optional<Patient> databasePatient = patientRepository.findById(identicalNumber);
        if (databasePatient.isPresent()) {
            throw new IllegalArgumentException(
                    ErrorMessageTemplate.ENTITY_ID_ALREADY_EXIST.getFormattedMessage(identicalNumber));
        }

        return patientRepository.saveAndFlush(patient);
    }

    @Override
    public Patient update(String identicalNumber, Patient patient) {
        if (!patientRepository.existsById(identicalNumber)) {
            throw new IllegalArgumentException(
                    ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(identicalNumber));
        }

        return patientRepository.saveAndFlush(patient);
    }

    @Override
    public void delete(String identicalNumber) {
        if (!patientRepository.existsById(identicalNumber)) {
            throw new IllegalArgumentException(
                    ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(identicalNumber));
        }

        patientRepository.deleteById(identicalNumber);
    }


    @Override
    public Patient getById(String identicalNumber) {
        if (!patientRepository.existsById(identicalNumber)) {
            throw new IllegalArgumentException(
                    ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(identicalNumber));
        }

        return patientRepository.findById(identicalNumber).get();
    }
}
