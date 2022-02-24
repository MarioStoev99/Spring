package com.example.medicalrecordf107646.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.Assert;

import com.example.medicalrecordf107646.model.Doctor;
import com.example.medicalrecordf107646.model.Patient;
import com.example.medicalrecordf107646.repository.PatientRepository;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    private static final String NAME = "Mario";
    private static final String ID = "1234";

    @Mock
    private PatientRepository patientRepository;

    private PatientService patientService;

    @BeforeEach
    void init() {
        patientService = new PatientServiceImpl(patientRepository);
    }

    @Test
    public void test_create() {
        Patient patient = getPatient();

        Mockito.when(patientRepository.saveAndFlush(any())).thenReturn(patient);

        Patient actual = patientService.create(patient);

        Assert.assertEquals(actual.getName(), NAME);
        Assert.assertEquals(actual.getIdenticalNumber(), ID);

        verify(patientRepository, times(1)).saveAndFlush(any());
    }


    @Test
    public void test_update_directorNotExist() {
        Patient patient = getPatient();

        Mockito.when(patientRepository.existsById(any())).thenReturn(false);

        Assert.assertThrows(IllegalArgumentException.class, () -> patientService.update(ID, patient));
    }

    @Test
    public void test_update() {
        String name = "Oxoboxo";
        Patient updated = getPatient();
        updated.setName(name);

        Mockito.when(patientRepository.existsById(any())).thenReturn(true);
        Mockito.when(patientRepository.saveAndFlush(any())).thenReturn(updated);

        Patient actual = patientService.update(ID, updated);

        Assert.assertEquals(actual.getName(), name);

        verify(patientRepository, times(1)).existsById(any());
        verify(patientRepository, times(1)).saveAndFlush(any());
    }

    @Test
    public void test_delete() {
        Mockito.when(patientRepository.existsById(any())).thenReturn(true);

        patientService.delete(ID);

        verify(patientRepository, times(1)).existsById(any());
        verify(patientRepository, times(1)).deleteById(any());
    }

    @Test
    public void test_delete_directorNotExist() {
        Mockito.when(patientRepository.existsById(any())).thenReturn(false);

        Assert.assertThrows(IllegalArgumentException.class, () -> patientService.delete(ID));

        verify(patientRepository, times(1)).existsById(any());
        verify(patientRepository, times(0)).deleteById(any());
    }

    @Test
    public void test_getById_patientNotExist() {
        Mockito.when(patientRepository.existsById(any())).thenReturn(false);

        Assert.assertThrows(IllegalArgumentException.class, () -> patientService.getById(ID));

        verify(patientRepository, times(1)).existsById(any());
        verify(patientRepository, times(0)).findById(any());
    }

    @Test
    public void test_getById() {
        Patient patient = getPatient();

        Mockito.when(patientRepository.existsById(any())).thenReturn(true);
        Mockito.when(patientRepository.findById(any())).thenReturn(Optional.ofNullable(patient));

        Patient actual = patientService.getById(ID);

        Assert.assertEquals(actual.getName(), NAME);
        Assert.assertEquals(actual.getIdenticalNumber(), ID);

        verify(patientRepository, times(1)).existsById(any());
        verify(patientRepository, times(1)).findById(any());
    }

    private Patient getPatient() {
        return Patient.builder()
                .doctor(new Doctor())
                .identicalNumber(ID)
                .name(NAME)
                .build();
    }
}
