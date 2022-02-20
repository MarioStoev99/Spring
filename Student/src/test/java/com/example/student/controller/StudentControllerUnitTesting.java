package com.example.student.controller;

import com.example.student.exception.StudentNotFoundException;
import com.example.student.model.Student;
import com.example.student.repository.StudentPostgreRepository;
import com.example.student.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StudentService.class)
public class StudentControllerUnitTesting {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Test
    public void testGetStudents() throws Exception {
        // new Student("Mario",23),new Student("Petar",25)
        List<Student> students = List.of();
        when(studentService.getStudents()).thenReturn(students);

        mockMvc.perform(get("/students")).
                andExpect(status().isOk()).
                andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect((ResultMatcher) content().json("[]"));

        verify(studentService, times(1)).getStudents();
    }

    @Test
    public void testGetStudentNotFound() throws Exception {
        when(studentService.getStudent(1L)).thenThrow(StudentNotFoundException.class);

        mockMvc.perform(get("/student/1")).
                andExpect(status().isNotFound());

        verify(studentService, times(1)).getStudents();
    }

    @Test
    public void testGetStudent() throws Exception {
        when(studentService.getStudent(1L)).thenReturn(new Student("Mario",23));

        mockMvc.perform(get("/student/1")).
                andExpect(status().isOk()).
                andExpect((ResultMatcher) content().contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect((ResultMatcher) content().json("\"name:\": \"Mario\",\n" +
                        "  \"age\": 23"));

        verify(studentService, times(1)).getStudents();
    }

    @Test
    public void testRemoveStudentIdNotFound() throws Exception {
        when(studentService.deleteStudent(1L)).thenThrow(StudentNotFoundException.class);

        mockMvc.perform(get("/student/1")).
                andExpect(status().isNotFound());

        verify(studentService, times(1)).getStudents();
    }
}
