package com.example.student.controller;

import com.example.student.exception.StudentNotFoundException;
import com.example.student.model.Student;
import com.example.student.repository.StudentPostgreRepository;
import com.example.student.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentPostgreRepository studentRepository;

    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        studentService = new StudentService(studentRepository);
    }

    @Test
    public void testGetStudents() {
        when(studentRepository.findAll()).thenReturn(List.of(new Student("Mario", 23)));

        List<Student> students = studentService.getStudents();
        Student student = students.get(0);

        assertEquals(1, students.size());
        assertEquals("Mario", student.getName());
        assertEquals(23, student.getAge());
    }

    @Test
    public void testGetStudentNotFound() {
        when(studentRepository.findById(1L)).thenThrow(new StudentNotFoundException(""));

        assertThrows(StudentNotFoundException.class, () -> studentService.getStudent(1L));
    }

    @Test
    public void testGetStudent() {
        when(studentRepository.findById(any())).thenReturn(Optional.of(new Student("Petar", 24)));
        Student student = studentService.getStudent(2L);

        assertEquals("Petar", student.getName());
        assertEquals(24, student.getAge());
    }

    @Test
    public void testRemoveStudentIdNotFound() {
        when(studentRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.getStudent(1L));
    }

    @Test
    public void testRemoveStudent() {
        Student databaseStudent = new Student("Kristiyan",25);
        studentService.addStudent(new Student("Mario",23));
        studentService.addStudent(databaseStudent);

        when(studentRepository.findById(any())).thenReturn(Optional.of(databaseStudent));
        when(studentRepository.findAll()).thenReturn(List.of(new Student("Mario",23)));

        List<Student> students = studentService.deleteStudent(2L);
        Student student = students.get(0);

        assertEquals(1, students.size());
        assertEquals("Mario", student.getName());
        assertEquals(23, student.getAge());
    }

    @Test
    public void testUpdateStudentNotFound() {
        when(studentRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.updateStudent(1L,new Student("Mario",23)));
    }

    @Test
    public void testUpdateStudentInvalidArgument() {
        when(studentRepository.findById(any())).thenReturn(Optional.of(new Student("Mario",23)));

        assertThrows(IllegalArgumentException.class, () -> studentService.updateStudent(1L,new Student(null,23)));
    }

    @Test
    public void testUpdateStudentSuccess() {
        Student kristiyan = new Student("Kristiyan",25);
        Student mario = new Student("Mario",23);
        Student petar = new Student("Petar",28);
        studentService.addStudent(mario);
        studentService.addStudent(kristiyan);

        when(studentRepository.findById(any())).thenReturn(Optional.of(kristiyan));
        when(studentRepository.findAll()).thenReturn(List.of(mario,petar));

        List<Student> expected = List.of(mario,petar);
        List<Student> actual = studentService.updateStudent(2L,petar);

        assertEquals(2, actual.size());
        assertArrayEquals(expected.toArray(),actual.toArray());
    }

}
