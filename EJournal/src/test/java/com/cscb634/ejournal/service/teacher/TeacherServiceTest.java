package com.cscb634.ejournal.service.teacher;

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
import com.cscb634.ejournal.model.Teacher;
import com.cscb634.ejournal.repository.DirectorRepository;
import com.cscb634.ejournal.repository.TeacherRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private DirectorRepository directorRepository;

    private TeacherService teacherService;

    @BeforeEach
    void init() {
        teacherService = new TeacherServiceImpl(teacherRepository, directorRepository);
    }

    @Test
    public void test_create() {
        Teacher teacher = Teacher.builder()
                .address("test2")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test")
                .lastName("test1")
                .build();

        Mockito.when(teacherRepository.saveAndFlush(any())).thenReturn(teacher);

        Teacher actual = teacherService.create(teacher);

        Assert.assertEquals(actual.getFirstName(), "test");
        Assert.assertEquals(actual.getLastName(), "test1");
        Assert.assertEquals(actual.getAddress(), "test2");
        Assert.assertEquals(actual.getAge(), 30);

        verify(teacherRepository, times(1)).saveAndFlush(any());
    }


    @Test
    public void test_update_teacherNotExist() {
        Teacher teacher = Teacher.builder()
                .address("test2")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test")
                .lastName("test1")
                .build();

        Mockito.when(teacherRepository.existsById(any())).thenReturn(false);

        Assert.assertThrows(IllegalStateException.class, () -> teacherService.update(UUID.randomUUID(), teacher));
    }

    @Test
    public void test_update() {
        Teacher teacher = Teacher.builder()
                .address("test3")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test1")
                .lastName("test2")
                .build();

        Teacher updated = Teacher.builder()
                .address("updated3")
                .id(UUID.randomUUID())
                .age(40)
                .firstName("updated1")
                .lastName("updated2")
                .build();

        Mockito.when(teacherRepository.existsById(any())).thenReturn(true);
        Mockito.when(teacherRepository.saveAndFlush(any())).thenReturn(updated);

        Teacher actual = teacherService.update(UUID.randomUUID(), updated);

        Assert.assertEquals(actual.getFirstName(), "updated1");
        Assert.assertEquals(actual.getLastName(), "updated2");
        Assert.assertEquals(actual.getAddress(), "updated3");
        Assert.assertEquals(actual.getAge(), 40);

        verify(teacherRepository, times(1)).existsById(any());
        verify(teacherRepository, times(1)).saveAndFlush(any());
    }

    @Test
    public void test_delete() {
        Mockito.when(teacherRepository.existsById(any())).thenReturn(true);

        teacherService.delete(UUID.randomUUID());

        verify(teacherRepository, times(1)).existsById(any());
        verify(teacherRepository, times(1)).deleteById(any());
    }

    @Test
    public void test_delete_directorNotExist() {
        Mockito.when(teacherRepository.existsById(any())).thenReturn(false);

        Assert.assertThrows(IllegalStateException.class, () -> teacherService.delete(UUID.randomUUID()));

        verify(teacherRepository, times(1)).existsById(any());
        verify(teacherRepository, times(0)).deleteById(any());
    }

    @Test
    public void test_getTeachers() {
        Teacher teacher = Teacher.builder()
                .address("test1")
                .build();

        Teacher teacher1 = Teacher.builder()
                .address("test2")
                .build();

        Mockito.when(directorRepository.findById(any())).thenReturn(Optional.ofNullable(Director.builder().build()));
        Mockito.when(teacherRepository.findAll()).thenReturn(List.of(teacher1, teacher));

        List<Teacher> actualSchools = teacherService.getTeachers(UUID.randomUUID());

        Assert.assertEquals(actualSchools.size(), 2);

        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    public void test_getTeachers_directorNotExist() {
        Mockito.when(directorRepository.findById(any())).thenReturn(Optional.empty());

        Assert.assertThrows(EntityNotFoundException.class, () -> teacherService.getTeachers(UUID.randomUUID()));

        verify(teacherRepository, times(0)).findAll();
    }
}
