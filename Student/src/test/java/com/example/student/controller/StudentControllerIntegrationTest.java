package com.example.student.controller;

import com.example.student.model.Student;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class StudentControllerIntegrationTest {

    private static final String HOST = "http://localhost:";
    private static final String PATH = "/student";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetStudents() {
        ResponseEntity<String> response = restTemplate.getForEntity(HOST + port + PATH, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetStudent() {
        ResponseEntity<String> response = restTemplate.getForEntity(HOST + port + PATH + "/1", String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void testAddStudent() {
        HttpEntity<Student> student = new HttpEntity<>(new Student("Mario", 23));
        ResponseEntity<String> response = restTemplate.postForEntity(HOST + port + PATH, student, String.class);

        assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
    }

    @Test
    public void testRemoveStudent() {
        HttpEntity<Student> student = new HttpEntity<>(new Student("Mario", 23));
        restTemplate.postForEntity(HOST + port + PATH, student, String.class);

        restTemplate.delete(HOST + port + PATH + "/1");

        ResponseEntity<String> response = restTemplate.getForEntity(HOST + port + PATH + "/1", String.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }

    @Test
    public void testUpdateStudent() {
        HttpEntity<Student> student = new HttpEntity<>(new Student("Mario", 23));
        restTemplate.postForEntity(HOST + port + PATH, student, String.class);

        HttpEntity<Student> updatedStudent = new HttpEntity<>(new Student("Petur", 27));
        restTemplate.put(HOST + port + PATH + "/1", updatedStudent);

        Student response = restTemplate.getForObject(HOST + port + PATH + "/1", Student.class);
        assertThat(response.getName(),notNullValue());
        assertThat(response.getId(),is(1));
    }
}
