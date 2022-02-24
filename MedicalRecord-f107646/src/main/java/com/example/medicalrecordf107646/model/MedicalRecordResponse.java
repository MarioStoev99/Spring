package com.example.medicalrecordf107646.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicalRecordResponse {

    private UUID id;

    private List<Doctor> doctors;

    private Patient patient;

    private String diagnosis;

    private String treatment;

    @JsonProperty("sick_days")
    private int sickDays;

    @JsonProperty("created_at")
    private Instant createdAt;
}
