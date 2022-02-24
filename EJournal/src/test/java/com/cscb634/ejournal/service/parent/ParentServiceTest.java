package com.cscb634.ejournal.service.parent;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
import com.cscb634.ejournal.model.Parent;
import com.cscb634.ejournal.model.School;
import com.cscb634.ejournal.model.Student;
import com.cscb634.ejournal.repository.DirectorRepository;
import com.cscb634.ejournal.repository.ParentRepository;
import com.cscb634.ejournal.repository.StudentRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
class ParentServiceTest {

    @Mock
    private ParentRepository parentRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private DirectorRepository directorRepository;

    private ParentService parentService;

    @BeforeEach
    void init() {
        parentService = new ParentServiceImpl(parentRepository, studentRepository, directorRepository);
    }

    @Test
    void test_create() {
        Parent parent = Parent.builder()
                .address("test2")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test")
                .lastName("test1")
                .build();

        Mockito.when(parentRepository.saveAndFlush(any())).thenReturn(parent);

        Parent actual = parentService.create(parent);

        Assert.assertEquals(actual.getFirstName(), "test");
        Assert.assertEquals(actual.getLastName(), "test1");
        Assert.assertEquals(actual.getAddress(), "test2");
        Assert.assertEquals(actual.getAge(), 30);

        verify(parentRepository, times(1)).saveAndFlush(any());
    }


    @Test
    void test_update_parentNotExist() {
        Parent parent = Parent.builder()
                .address("test2")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test")
                .lastName("test1")
                .build();

        Mockito.when(parentRepository.existsById(any())).thenReturn(false);

        Assert.assertThrows(IllegalStateException.class, () -> parentService.update(UUID.randomUUID(), parent));
    }

    @Test
    void test_update() {
        Parent parent = Parent.builder()
                .address("test3")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test1")
                .lastName("test2")
                .build();

        Parent updated = Parent.builder()
                .address("updated3")
                .id(UUID.randomUUID())
                .age(40)
                .firstName("updated1")
                .lastName("updated2")
                .build();

        Mockito.when(parentRepository.existsById(any())).thenReturn(true);
        Mockito.when(parentRepository.saveAndFlush(any())).thenReturn(updated);

        Parent actual = parentService.update(UUID.randomUUID(), updated);

        Assert.assertEquals(actual.getFirstName(), "updated1");
        Assert.assertEquals(actual.getLastName(), "updated2");
        Assert.assertEquals(actual.getAddress(), "updated3");
        Assert.assertEquals(actual.getAge(), 40);

        verify(parentRepository, times(1)).existsById(any());
        verify(parentRepository, times(1)).saveAndFlush(any());
    }

    @Test
    void test_delete() {
        Mockito.when(parentRepository.existsById(any())).thenReturn(true);

        parentService.delete(UUID.randomUUID());

        verify(parentRepository, times(1)).existsById(any());
        verify(parentRepository, times(1)).deleteById(any());
    }

    @Test
    void test_delete_parentNotExist() {
        Mockito.when(parentRepository.existsById(any())).thenReturn(false);

        Assert.assertThrows(IllegalStateException.class, () -> parentService.delete(UUID.randomUUID()));

        verify(parentRepository, times(1)).existsById(any());
        verify(parentRepository, times(0)).deleteById(any());
    }

    @Test
    void test_getChildren() {
        Student student = Student.builder()
                .firstName("Mario")
                .lastName("Stoev")
                .id(UUID.randomUUID())
                .age(24)
                .address("address")
                .build();

        Parent parent = Parent.builder()
                .address("test3")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test1")
                .lastName("test2")
                .students(List.of(student))
                .build();

        when(parentRepository.findById(any())).thenReturn(Optional.of(parent));

        List<Student> children = parentService.getChildren(UUID.randomUUID());
        Student actual = children.get(0);

        Assert.assertEquals(actual.getFirstName(), "Mario");
        Assert.assertEquals(actual.getLastName(), "Stoev");
        Assert.assertEquals(actual.getAddress(), "address");
        Assert.assertEquals(actual.getAge(), 24);
    }

    //Test
    void test_addChild() {
        Student student = Student.builder()
                .firstName("Mario")
                .lastName("Stoev")
                .id(UUID.randomUUID())
                .age(24)
                .address("address")
                .build();

        Parent parent = Parent.builder()
                .address("test3")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test1")
                .lastName("test2")
                .students(List.of(student))
                .build();

        when(parentRepository.findById(any())).thenReturn(Optional.of(parent));
        when(studentRepository.findById(any())).thenReturn(Optional.of(student));

        Parent actualParent  = parentService.addChild(UUID.randomUUID(), UUID.randomUUID());
        Student actualStudent = actualParent.getStudents().get(0);

        Assert.assertEquals(actualStudent.getFirstName(), "Mario");
        Assert.assertEquals(actualStudent.getLastName(), "Stoev");
        Assert.assertEquals(actualStudent.getAddress(), "address");
        Assert.assertEquals(actualStudent.getAge(), 24);
    }

    @Test
    void test_addChild_parentNotExist() {
        Student student = Student.builder()
                .firstName("Mario")
                .lastName("Stoev")
                .id(UUID.randomUUID())
                .age(24)
                .address("address")
                .build();

        when(studentRepository.findById(any())).thenReturn(Optional.of(student));

        Assert.assertThrows(EntityNotFoundException.class, () -> parentService.addChild(UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test
    void test_addChild_childNotExist() {
        Assert.assertThrows(EntityNotFoundException.class, () -> parentService.addChild(UUID.randomUUID(), UUID.randomUUID()));
    }

    @Test
    void test_getParent() {
        Student student = Student.builder()
                .firstName("Mario")
                .lastName("Stoev")
                .id(UUID.randomUUID())
                .age(24)
                .address("address")
                .build();

        Parent parent = Parent.builder()
                .address("test3")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test1")
                .lastName("test2")
                .students(List.of(student))
                .build();

        when(parentRepository.findById(any())).thenReturn(Optional.of(parent));

        List<Student> children = parentService.getChildren(UUID.randomUUID());
        Student actual = children.get(0);

        Assert.assertEquals(actual.getFirstName(), "Mario");
        Assert.assertEquals(actual.getLastName(), "Stoev");
        Assert.assertEquals(actual.getAddress(), "address");
        Assert.assertEquals(actual.getAge(), 24);
    }

    @Test
    void test_getChildren_parentNotExist() {
        when(parentRepository.findById(any())).thenReturn(Optional.empty());

        Assert.assertThrows(EntityNotFoundException.class, () -> parentService.getChildren(UUID.randomUUID()));
    }
}
