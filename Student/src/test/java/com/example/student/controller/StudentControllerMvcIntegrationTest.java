package com.example.student.controller;

import com.example.student.model.Student;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.lang.reflect.Type;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerMvcIntegrationTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private static final Gson gson = new Gson();

    @BeforeClass
    public void init() {
        mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testGetStudents() throws Exception {
        mockMvc.perform(get("/student"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[]"));
    }

    @Test
    public void testGetStudentNotFound() throws Exception {
        mockMvc.perform(get("/student/1231221"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddStudentInvalidClientJson() throws Exception {
        Student student = new Student(null, 160);
        String jsonString = gson.toJson(student);

        mockMvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isBadRequest());
    }

    @Test(dependsOnMethods = "testGetStudents")
    public void testAddStudent() throws Exception {
        Student student0 = new Student("Mario", 23);
        String jsonString0 = gson.toJson(student0);
        Student student1 = new Student("Petar", 28);
        String jsonString1 = gson.toJson(student1);
        Student student2 = new Student("Kristiyan", 29);
        String jsonString2 = gson.toJson(student2);

        mockMvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON).content(jsonString0))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        mockMvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON).content(jsonString1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        String json = mockMvc.perform(post("/student").contentType(MediaType.APPLICATION_JSON).content(jsonString2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        Type type = new TypeToken<List<Student>>() {
        }.getType();
        List<Student> expected = List.of(student0, student1, student2);
        List<Student> actual = gson.fromJson(json, type);
        System.out.println(actual.toString());
        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test(dependsOnMethods = "testAddStudent")
    public void testGetStudent() throws Exception {
        Student student = new Student("Mario", 23);
        String jsonString = gson.toJson(student);

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(jsonString));
    }

    @Test
    public void testDeleteStudentNotFound() throws Exception {
        mockMvc.perform(delete("/student/1231221"))
                .andExpect(status().isNotFound());
    }

    @Test(dependsOnMethods = "testAddStudent")
    public void testRemoveStudent() throws Exception {
        String json = mockMvc.perform(delete("/student/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        Student student1 = new Student("Petar", 28);
        Student student2 = new Student("Kristiyan", 29);
        List<Student> expected = List.of(student1, student2);

        Type type = new TypeToken<List<Student>>() {
        }.getType();
        List<Student> actual = gson.fromJson(json, type);

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

    @Test
    public void testUpdateStudentNotFound() throws Exception {
        Student student = new Student("Mario", 23);
        String jsonString = gson.toJson(student);

        mockMvc.perform(put("/student/1231221")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isNotFound());
    }

    @Test(dependsOnMethods = "testAddStudent")
    public void testUpdateStudentEqualNames() throws Exception {
        Student student = new Student("Kristiyan", 35);
        String jsonString = gson.toJson(student);

        mockMvc.perform(put("/student/3")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    public void testUpdateStudentInvalidClientJson() throws Exception {
        Student student = new Student(null, 160);
        String jsonString = gson.toJson(student);

        mockMvc.perform(put("/student/3")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonString))
                .andExpect(status().isBadRequest());
    }

    @Test(dependsOnMethods = "testRemoveStudent")
    public void testUpdateStudent() throws Exception {
        Student student1 = new Student("Kristiyan", 29);
        Student student2 = new Student("Nikolay", 35);
        String jsonString2 = gson.toJson(student2);
        List<Student> expected = List.of(student1, student2);

        String json = mockMvc.perform(put("/student/2")
                        .contentType(MediaType.APPLICATION_JSON).content(jsonString2))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse().getContentAsString();

        Type type = new TypeToken<List<Student>>() {}.getType();
        List<Student> actual = gson.fromJson(json, type);

        assertArrayEquals(expected.toArray(), actual.toArray());
    }

}
