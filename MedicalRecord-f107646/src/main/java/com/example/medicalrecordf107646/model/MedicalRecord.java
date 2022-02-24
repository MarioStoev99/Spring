package com.example.medicalrecordf107646.model;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "medical-record")
public class MedicalRecord {

    @Id
    @NotNull
    private UUID id;

    @NotEmpty
    @JsonProperty("doctor_ids")
    @ElementCollection(targetClass = UUID.class)
    @CollectionTable(name = "medical_record_doctors", joinColumns = @JoinColumn(name = "medical_record_id"))
    private List<UUID> doctorIds;

    @NotNull
    @JsonProperty("patient_id")
    private String patientId;

    @NotNull
    private String diagnosis;

    @NotNull
    private String treatment;

    @NotNull
    @Positive
    @JsonProperty("sick_days")
    private int sickDays;

    @NotNull
    @CreatedDate
    @JsonProperty("created_at")
    private Instant createdAt;

}
