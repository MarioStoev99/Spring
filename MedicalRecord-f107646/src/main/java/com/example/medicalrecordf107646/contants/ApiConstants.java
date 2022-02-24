package com.example.medicalrecordf107646.contants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ApiConstants {

    public static final String API_VERSION = "/v1";
    public static final String API_PREFIX = "/api";
    public static final String API_BASE = API_PREFIX + API_VERSION;

    public static final String DOCTOR_API = API_BASE + "/doctor";
    public static final String PATIENT_API = API_BASE + "/patient";
    public static final String MEDICAL_RECORD_API = API_BASE + "/medical-record";
}
