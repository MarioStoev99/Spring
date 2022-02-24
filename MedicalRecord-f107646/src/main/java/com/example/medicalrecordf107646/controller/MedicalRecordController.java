package com.example.medicalrecordf107646.controller;

import static com.example.medicalrecordf107646.contants.ApiConstants.MEDICAL_RECORD_API;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.medicalrecordf107646.model.MedicalRecord;
import com.example.medicalrecordf107646.model.MedicalRecordResponse;
import com.example.medicalrecordf107646.service.MedicalRecordService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(MEDICAL_RECORD_API)
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService medicalRecordService;

    @PostMapping
    public ResponseEntity<MedicalRecordResponse> create(@RequestBody @Valid MedicalRecord medicalRecord) {
        return ResponseEntity.ok(medicalRecordService.create(medicalRecord));
    }

    @GetMapping(value = "/doctor/visits/{doctorId}")
    public ResponseEntity<Integer> getVisits(@PathVariable("doctorId") @NotNull UUID doctorId) {
        return ResponseEntity.ok(medicalRecordService.getDoctorVisitorsNumber(doctorId));
    }

    @GetMapping(value = "/doctor/{doctorId}")
    public ResponseEntity<List<MedicalRecordResponse>> getAll(@PathVariable("doctorId") @NotNull UUID doctorId) {
        return ResponseEntity.ok(medicalRecordService.getAll(doctorId));
    }

    @GetMapping(value = "/patient/{patientId}")
    public ResponseEntity<List<MedicalRecordResponse>> get(@PathVariable("patientId") @NotNull String patientId) {
        return ResponseEntity.ok(medicalRecordService.get(patientId));
    }

    @GetMapping(value = "/diagnosis/{diagnosis}")
    public ResponseEntity<List<MedicalRecordResponse>> getByDiagnosis(
            @PathVariable("diagnosis") @NotNull String diagnosis) {
        return ResponseEntity.ok(medicalRecordService.getByDiagnosis(diagnosis));
    }
}
