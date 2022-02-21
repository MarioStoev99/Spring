package com.example.student.controller;

import com.example.student.model.Student;
import com.example.student.service.StudentService;
import com.google.gson.Gson;
import net.bytebuddy.asm.Advice;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testng.annotations.Test;
import java.util.List;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentControllerMvcIntegrationTest {

//    @LocalServerPort
//    private int port;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    private static final Gson gson = new Gson();

    @Test
    public void testGetStudents() throws Exception {
        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));

        Mockito.verify(studentService, Mockito.times(1)).getStudents();
    }

    @Test(dependsOnMethods = "testGetStudents")
    public void testAddStudent() throws Exception {
        Student student = new Student("Mario", 23);
        Student student1 = new Student("Petar", 23);
        Student student2 = new Student("Nikolay", 23);
        String jsonString = gson.toJson(student);
        String jsonString1 = gson.toJson(student1);
        String jsonString2 = gson.toJson(student2);
        String jsonList = new Gson().toJson(List.of(jsonString, jsonString1, jsonString2));

        mockMvc.perform(post("/student").content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonList));

        Mockito.verify(studentService, Mockito.times(1)).addStudent(any());
    }

    @Test(dependsOnMethods = "testAddStudent")
    public void testGetStudent() throws Exception {
        Student student = new Student("Mario", 23);
        String jsonString = gson.toJson(student);

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonString));

        Mockito.verify(studentService, Mockito.times(1)).getStudent(any());
    }

    @Test(dependsOnMethods = "testAddStudent")
    public void testRemoveStudent() throws Exception {
        Student student1 = new Student("Petar", 23);
        Student student2 = new Student("Nikolay", 23);
        String jsonString1 = gson.toJson(student1);
        String jsonString2 = gson.toJson(student2);
        String jsonList = new Gson().toJson(List.of(jsonString1, jsonString2));

        mockMvc.perform(delete("/student/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonList));

        Mockito.verify(studentService, Mockito.times(1)).getStudents();
    }

    @Test(dependsOnMethods = "testRemoveStudent")
    public void testUpdateStudent() throws Exception {
        Student student = new Student("Kiril", 29);
        String jsonString = gson.toJson(student);
        Student student1 = new Student("Nikolay", 23);
        String jsonString1 = gson.toJson(student1);

        String jsonList = new Gson().toJson(List.of(jsonString, jsonString1));
        mockMvc.perform(put("/student/2").content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonList));

        Mockito.verify(studentService, Mockito.times(1)).getStudents();
    }

}
