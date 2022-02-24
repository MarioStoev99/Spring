package com.example.medicalrecordf107646.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.Assert;

import com.example.medicalrecordf107646.model.Doctor;
import com.example.medicalrecordf107646.model.MedicalRecord;
import com.example.medicalrecordf107646.model.MedicalRecordResponse;
import com.example.medicalrecordf107646.model.Patient;
import com.example.medicalrecordf107646.repository.MedicalRecordRepository;

@ExtendWith(MockitoExtension.class)
public class MedicalServiceTest {

    @Mock
    private PatientService patientService;

    @Mock
    private DoctorService doctorService;

    @Mock
    private MedicalRecordRepository medicalRecordRepository;

    private MedicalRecordService medicalRecordService;

    @BeforeEach
    void init() {
        medicalRecordService = new MedicalRecordServiceImpl(patientService, doctorService, medicalRecordRepository);
    }

    @Test
    public void test_create() {
        String diagnosis = "diagnosis";
        Instant instant = Instant.now();
        int sickDays = 2;

        final MedicalRecord medicalRecord = MedicalRecord.builder()
                .sickDays(sickDays)
                .diagnosis(diagnosis)
                .createdAt(instant)
                .build();

        Mockito.when(medicalRecordRepository.saveAndFlush(any())).thenReturn(medicalRecord);

        MedicalRecordResponse actual = medicalRecordService.create(medicalRecord);

        Assert.assertEquals(actual.getDiagnosis(), diagnosis);
        Assert.assertEquals(actual.getSickDays(), sickDays);

        verify(medicalRecordRepository, times(1)).saveAndFlush(any());
    }

    @Test
    public void test_getByPatientId() {
        String patientId = "1234";
        String diagnosis = "diagnosis";

        Patient patient = Patient.builder()
                .identicalNumber(patientId)
                .name("name")
                .build();

        MedicalRecord medicalRecord = MedicalRecord.builder()
                .patientId(patientId)
                .diagnosis(diagnosis)
                .sickDays(3)
                .id(UUID.randomUUID())
                .build();

        List<MedicalRecord> medicalRecords = List.of(medicalRecord);

        Mockito.when(medicalRecordRepository.findAllByPatientId(any())).thenReturn(medicalRecords);
        Mockito.when(patientService.getById(any())).thenReturn(patient);

        List<MedicalRecordResponse> actual = medicalRecordService.get(patientId);

        MedicalRecordResponse medicalRecordResponse = actual.get(0);
        Assert.assertEquals(medicalRecordResponse.getSickDays(), 3);
        Assert.assertEquals(medicalRecordResponse.getDiagnosis(), diagnosis);
        Assert.assertEquals(medicalRecordResponse.getPatient().getIdenticalNumber(), patientId);

        verify(medicalRecordRepository, times(1)).findAllByPatientId(any());
    }

    @Test
    public void test_getByDiagnosis() {
        String patientId = "1234";
        String diagnosis = "diagnosis";

        Patient patient = Patient.builder()
                .identicalNumber(patientId)
                .name("name")
                .build();

        MedicalRecord medicalRecord = MedicalRecord.builder()
                .patientId(patientId)
                .diagnosis(diagnosis)
                .sickDays(3)
                .id(UUID.randomUUID())
                .build();

        List<MedicalRecord> medicalRecords = List.of(medicalRecord);

        Mockito.when(medicalRecordRepository.findAllByDiagnosis(any())).thenReturn(medicalRecords);
        Mockito.when(patientService.getById(any())).thenReturn(patient);

        List<MedicalRecordResponse> actual = medicalRecordService.getByDiagnosis(diagnosis);

        MedicalRecordResponse medicalRecordResponse = actual.get(0);
        Assert.assertEquals(medicalRecordResponse.getSickDays(), 3);
        Assert.assertEquals(medicalRecordResponse.getDiagnosis(), diagnosis);
        Assert.assertEquals(medicalRecordResponse.getPatient().getIdenticalNumber(), patientId);

        verify(medicalRecordRepository, times(1)).findAllByDiagnosis(any());
    }
}
