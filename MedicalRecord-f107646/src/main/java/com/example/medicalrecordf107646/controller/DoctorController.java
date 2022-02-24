package com.example.medicalrecordf107646.controller;

import static com.example.medicalrecordf107646.contants.ApiConstants.DOCTOR_API;

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

import com.example.medicalrecordf107646.model.Doctor;
import com.example.medicalrecordf107646.model.Patient;
import com.example.medicalrecordf107646.service.DoctorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(DOCTOR_API)
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    public ResponseEntity<Doctor> create(@RequestBody @Valid Doctor doctor) {
        return ResponseEntity.ok(doctorService.create(doctor));
    }

    @PutMapping(value = "/{doctorId}")
    public ResponseEntity<Doctor> update(@PathVariable("doctorId") UUID doctorId, @RequestBody @Valid Doctor doctor) {
        return ResponseEntity.ok(doctorService.update(doctorId, doctor));
    }

    @DeleteMapping(value = "/{doctorId}")
    public void delete(@PathVariable UUID doctorId) {
        doctorService.delete(doctorId);
    }

    @GetMapping(value = "/{doctorId}")
    public ResponseEntity<Doctor> getById(@PathVariable("doctorId") UUID doctorId) {
        return ResponseEntity.ok(doctorService.getById(doctorId));
    }

    @GetMapping(value = "/patients-number/{doctorId}")
    public ResponseEntity<Integer> getPatientsNumberByDoctorId(@PathVariable("doctorId") UUID doctorId) {
        return ResponseEntity.ok(doctorService.getById(doctorId).getPatients().size());
    }

    @PostMapping(value = "/add-patient/{doctorId}")
    public ResponseEntity<Doctor> addPatient(@PathVariable("doctorId") UUID doctorId, @RequestBody @Valid Patient patient) {
        return ResponseEntity.ok(doctorService.addPatient(doctorId, patient));
    }

}
