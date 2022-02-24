package com.example.medicalrecordf107646.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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
import com.example.medicalrecordf107646.repository.DoctorRepository;
import com.example.medicalrecordf107646.repository.PatientRepository;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    private static final String NAME = "Mario";
    private static final UUID ID = UUID.randomUUID();

    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private PatientRepository patientRepository;

    private DoctorService doctorService;

    @BeforeEach
    void init() {
        doctorService = new DoctorServiceImpl(doctorRepository, patientRepository);
    }

    @Test
    public void test_create() {
        Doctor doctor = getDoctor(ID);

        Mockito.when(doctorRepository.saveAndFlush(any())).thenReturn(doctor);

        Doctor actual = doctorService.create(doctor);

        Assert.assertEquals(actual.getName(), NAME);
        Assert.assertEquals(actual.getId(), ID);

        verify(doctorRepository, times(1)).saveAndFlush(any());
    }


    @Test
    public void test_update_directorNotExist() {
        Doctor doctor = getDoctor(ID);

        Mockito.when(doctorRepository.existsById(any())).thenReturn(false);

        Assert.assertThrows(IllegalArgumentException.class, () -> doctorService.update(ID, doctor));
    }

    @Test
    public void test_update() {
        String name = "Oxoboxo";
        Doctor updated = getDoctor(ID);
        updated.setName(name);

        Mockito.when(doctorRepository.existsById(any())).thenReturn(true);
        Mockito.when(doctorRepository.saveAndFlush(any())).thenReturn(updated);

        Doctor actual = doctorService.update(ID, updated);

        Assert.assertEquals(actual.getName(), name);

        verify(doctorRepository, times(1)).existsById(any());
        verify(doctorRepository, times(1)).saveAndFlush(any());
    }

    @Test
    public void test_delete() {
        Mockito.when(doctorRepository.existsById(any())).thenReturn(true);

        doctorService.delete(ID);

        verify(doctorRepository, times(1)).existsById(any());
        verify(doctorRepository, times(1)).deleteById(any());
    }

    @Test
    public void test_delete_directorNotExist() {
        Mockito.when(doctorRepository.existsById(any())).thenReturn(false);

        Assert.assertThrows(IllegalArgumentException.class, () -> doctorService.delete(ID));

        verify(doctorRepository, times(1)).existsById(any());
        verify(doctorRepository, times(0)).deleteById(any());
    }

    @Test
    public void test_getById_patientNotExist() {
        Mockito.when(doctorRepository.existsById(any())).thenReturn(false);

        Assert.assertThrows(IllegalArgumentException.class, () -> doctorService.getById(ID));

        verify(doctorRepository, times(1)).existsById(any());
        verify(doctorRepository, times(0)).findById(any());
    }

    @Test
    public void test_getById() {
        Doctor patient = getDoctor(ID);

        Mockito.when(doctorRepository.existsById(any())).thenReturn(true);
        Mockito.when(doctorRepository.findById(any())).thenReturn(Optional.ofNullable(patient));

        Doctor actual = doctorService.getById(ID);

        Assert.assertEquals(actual.getName(), NAME);
        Assert.assertEquals(actual.getId(), ID);

        verify(doctorRepository, times(1)).existsById(any());
        verify(doctorRepository, times(1)).findById(any());
    }

    @Test
    public void test_getAllById() {
        Doctor firstDoctor = getDoctor(ID);

        Mockito.when(doctorRepository.existsById(any())).thenReturn(true);
        Mockito.when(doctorRepository.findById(any())).thenReturn(Optional.ofNullable(firstDoctor));

        List<Doctor> actual = doctorService.getAllById(List.of(ID));

        Doctor first = actual.get(0);
        Assert.assertEquals(first.getName(), NAME);
        Assert.assertEquals(first.getId(), ID);

        verify(doctorRepository, times(1)).existsById(any());
        verify(doctorRepository, times(1)).findById(any());
    }

    private Doctor getDoctor(UUID id) {
        return Doctor.builder()
                .id(id)
                .name(NAME)
                .specialties(List.of("ears", "nose", "throat"))
                .build();
    }
}
