package com.example.medicalrecordf107646.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.medicalrecordf107646.contants.ErrorMessageTemplate;
import com.example.medicalrecordf107646.model.Doctor;
import com.example.medicalrecordf107646.model.Patient;
import com.example.medicalrecordf107646.repository.DoctorRepository;
import com.example.medicalrecordf107646.repository.PatientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {

    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;

    @Override
    public Doctor create(Doctor doctor) {
        UUID doctorId = doctor.getId();
        Optional<Doctor> databaseDoctor = doctorRepository.findById(doctorId);
        if (databaseDoctor.isPresent()) {
            throw new IllegalArgumentException(
                    ErrorMessageTemplate.ENTITY_ID_ALREADY_EXIST.getFormattedMessage(doctorId));
        }

        return doctorRepository.saveAndFlush(doctor);
    }

    @Override
    public Doctor update(UUID id, Doctor doctor) {
        if (!doctorRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(id));
        }

        return doctorRepository.saveAndFlush(doctor);
    }

    @Override
    public void delete(UUID id) {
        if (!doctorRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(id));
        }

        doctorRepository.deleteById(id);
    }


    @Override
    public Doctor getById(UUID id) {
        if (!doctorRepository.existsById(id)) {
            throw new IllegalArgumentException(
                    ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(id));
        }

        return doctorRepository.findById(id).get();
    }

    @Override
    public List<Doctor> getAllById(List<UUID> ids) {
        List<Doctor> doctors = new ArrayList<>();
        for (UUID id : ids) {
            Doctor doctor = getById(id);
            doctors.add(doctor);
        }

        return doctors;
    }

    @Override
    public Doctor addPatient(UUID id, Patient patient) {
        String identicalNumber = patient.getIdenticalNumber();
        Optional<Patient> databasePatient = patientRepository.findById(identicalNumber);
        if (databasePatient.isEmpty()) {
            throw new IllegalArgumentException(
                    ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(identicalNumber));
        }

        Optional<Doctor> dbDoctor = doctorRepository.findById(id);
        if (dbDoctor.isEmpty()) {
            throw new IllegalArgumentException(
                    ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(id));
        }

        Doctor doctor = dbDoctor.get();
        List<Patient> patients = doctor.getPatients();
        patients.add(patient);
        patient.setDoctor(doctor);
        doctorRepository.saveAndFlush(doctor);
        return doctor;
    }

}
