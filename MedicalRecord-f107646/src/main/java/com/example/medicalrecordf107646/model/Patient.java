package com.example.medicalrecordf107646.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "patient")
public class Patient {

    @Id
    @JsonProperty("identical_number")
    private String identicalNumber;

    private String name;

    @JsonProperty("paid_medical_insurance")
    private boolean paidMedicalInsurance;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

}
