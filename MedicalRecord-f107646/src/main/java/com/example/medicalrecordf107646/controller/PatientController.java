package com.example.medicalrecordf107646.controller;

import static com.example.medicalrecordf107646.contants.ApiConstants.PATIENT_API;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.medicalrecordf107646.model.Patient;
import com.example.medicalrecordf107646.service.PatientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(PATIENT_API)
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping
    public ResponseEntity<Patient> create(@RequestBody Patient patient) {
        return ResponseEntity.ok(patientService.create(patient));
    }

    @PutMapping(value = "/{patientId}")
    public ResponseEntity<Patient> update(@PathVariable("patientId") String patientId,
                                          @RequestBody @Valid Patient patient) {
        return ResponseEntity.ok(patientService.update(patientId, patient));
    }

    @DeleteMapping(value = "/{patientId}")
    public void delete(@PathVariable String patientId) {
        patientService.delete(patientId);
    }

    @GetMapping(value = "/{patientId}")
    public ResponseEntity<Patient> getById(@PathVariable("patientId") String patientId) {
        return ResponseEntity.ok(patientService.getById(patientId));
    }
}
