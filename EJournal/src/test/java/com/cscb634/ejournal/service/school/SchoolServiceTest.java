package com.cscb634.ejournal.service.school;

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

import com.cscb634.ejournal.model.Director;
import com.cscb634.ejournal.model.School;
import com.cscb634.ejournal.model.Student;
import com.cscb634.ejournal.repository.DirectorRepository;
import com.cscb634.ejournal.repository.SchoolRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class SchoolServiceTest {

    @Mock
    private SchoolRepository schoolRepository;

    @Mock
    private DirectorRepository directorRepository;

    private SchoolService schoolService;

    @BeforeEach
    void init() {
        schoolService = new SchoolServiceImpl(schoolRepository, directorRepository);
    }

    @Test
    public void test_create() {
        School school = School.builder()
                .address("test2")
                .name("96SU")
                .build();

        Mockito.when(schoolRepository.saveAndFlush(any())).thenReturn(school);

        School actual = schoolService.create(school);

        Assert.assertEquals(actual.getAddress(), "test2");
        Assert.assertEquals(actual.getName(), "96SU");

        verify(schoolRepository, times(1)).saveAndFlush(any());
    }

    //@Test
    public void test_addStudent() {
        Student student = Student.builder()
                .address("test2")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test")
                .lastName("test1")
                .build();

        School school = School.builder()
                .address("test2")
                .name("96SU")
                .students(List.of(student))
                .build();

        Mockito.when(schoolRepository.findById(any()))
                .thenReturn(Optional.of(School.builder().students(List.of()).build()));
        Mockito.when(schoolRepository.saveAndFlush(any())).thenReturn(school);

        School actualSchool = schoolService.addStudent(UUID.randomUUID(), student);
        Student actualStudent = actualSchool.getStudents().get(0);

        Assert.assertEquals(actualStudent.getFirstName(), "test");
        Assert.assertEquals(actualStudent.getLastName(), "test1");

        verify(schoolRepository, times(1)).saveAndFlush(any());
    }

    @Test
    public void test_addStudent_providedIdExist() {
        Student student = Student.builder()
                .address("test2")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test")
                .lastName("test1")
                .build();

        School school = School.builder()
                .address("test2")
                .name("96SU")
                .students(List.of(student))
                .build();

        Mockito.when(schoolRepository.findById(any()))
                .thenReturn(Optional.of(school));

        Assert.assertThrows(IllegalArgumentException.class, () -> schoolService.addStudent(UUID.randomUUID(), student));

        verify(schoolRepository, times(0)).saveAndFlush(any());
    }

    @Test
    public void test_removeStudent() {
        Student student = Student.builder()
                .address("test2")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test")
                .lastName("test1")
                .build();

        School school = School.builder()
                .address("test2")
                .name("96SU")
                .students(List.of(student))
                .build();

        Mockito.when(schoolRepository.findById(any())).thenReturn(Optional.of(school));
        Mockito.when(schoolRepository.saveAndFlush(any())).thenReturn(School.builder().students(List.of()).build());

        School actualSchool = schoolService.removeStudent(UUID.randomUUID(), UUID.randomUUID());
        List<Student> actualStudents = actualSchool.getStudents();

        Assert.assertEquals(actualStudents.size(), 0);

        verify(schoolRepository, times(1)).saveAndFlush(any());
    }

    @Test
    public void test_getSchools() {
        School school = School.builder()
                .address("test2")
                .name("96SU")
                .build();

        School school1 = School.builder()
                .address("test2")
                .name("96SU")
                .build();

        Mockito.when(directorRepository.findById(any())).thenReturn(Optional.ofNullable(Director.builder().build()));
        Mockito.when(schoolRepository.findAll()).thenReturn(List.of(school1, school));

        List<School> actualSchools = schoolService.getSchools(UUID.randomUUID());

        Assert.assertEquals(actualSchools.size(), 2);

        verify(schoolRepository, times(1)).findAll();
    }

    @Test
    public void test_getSchools_directorNotExist() {
        Mockito.when(directorRepository.findById(any())).thenReturn(Optional.empty());

        Assert.assertThrows(EntityNotFoundException.class, () -> schoolService.getSchools(UUID.randomUUID()));

        verify(schoolRepository, times(0)).findAll();
    }

}

