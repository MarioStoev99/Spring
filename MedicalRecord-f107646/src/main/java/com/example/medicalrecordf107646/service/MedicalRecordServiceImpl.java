package com.example.medicalrecordf107646.service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.medicalrecordf107646.contants.ErrorMessageTemplate;
import com.example.medicalrecordf107646.model.Doctor;
import com.example.medicalrecordf107646.model.MedicalRecord;
import com.example.medicalrecordf107646.model.MedicalRecordResponse;
import com.example.medicalrecordf107646.model.Patient;
import com.example.medicalrecordf107646.repository.MedicalRecordRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicalRecordServiceImpl implements MedicalRecordService {

    private final PatientService patientService;
    private final DoctorService doctorService;
    private final MedicalRecordRepository medicalRecordRepository;

    @Override
    public MedicalRecordResponse create(MedicalRecord medicalRecord) {
        medicalRecordRepository.saveAndFlush(medicalRecord);

        return createMedicalRecordResponse(medicalRecord);

    }

    @Override
    public List<MedicalRecordResponse> get(String patientId) {
        List<MedicalRecordResponse> medicalRecordResponses = new ArrayList<>();
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAllByPatientId(patientId);
        for (MedicalRecord medicalRecord : medicalRecords) {
            medicalRecordResponses.add(createMedicalRecordResponse(medicalRecord));
        }

        return medicalRecordResponses;
    }

    @Override
    public List<MedicalRecordResponse> getAll(UUID doctorId) {
        Doctor doctor = doctorService.getById(doctorId);
        if (doctor == null) {
            throw new IllegalArgumentException(
                    ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(doctorId));
        }

        List<MedicalRecordResponse> medicalRecordResponses = new ArrayList<>();
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
        for (MedicalRecord medicalRecord : medicalRecords) {
            medicalRecordResponses.add(createMedicalRecordResponse(medicalRecord));
        }

        return medicalRecordResponses;
    }

    @Override
    public Integer getDoctorVisitorsNumber(UUID doctorId) {
        Doctor doctor = doctorService.getById(doctorId);
        if (doctor == null) {
            throw new IllegalArgumentException(
                    ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(doctorId));
        }

        int number = 0;
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAll();
        for (MedicalRecord medicalRecord : medicalRecords) {
            List<UUID> doctorIds = medicalRecord.getDoctorIds();
            for (UUID id : doctorIds) {
                if (id.equals(doctorId)) {
                    number++;
                }
            }
        }

        return number;
    }

    @Override
    public List<MedicalRecordResponse> getByDiagnosis(String diagnosis) {
        List<MedicalRecordResponse> medicalRecordResponses = new ArrayList<>();
        List<MedicalRecord> medicalRecords = medicalRecordRepository.findAllByDiagnosis(diagnosis);
        for (MedicalRecord medicalRecord : medicalRecords) {
            medicalRecordResponses.add(createMedicalRecordResponse(medicalRecord));
        }

        return medicalRecordResponses;
    }

    private MedicalRecordResponse createMedicalRecordResponse(MedicalRecord medicalRecord) {
        Patient patient = patientService.getById(medicalRecord.getPatientId());
        List<Doctor> doctors = doctorService.getAllById(medicalRecord.getDoctorIds());

        return MedicalRecordResponse.builder()
                .id(medicalRecord.getId())
                .treatment(medicalRecord.getTreatment())
                .createdAt(Instant.now())
                .doctors(doctors)
                .diagnosis(medicalRecord.getDiagnosis())
                .patient(patient)
                .sickDays(medicalRecord.getSickDays())
                .build();
    }
}
