package com.cscb634.ejournal.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cscb634.ejournal.constants.Constants;
import com.cscb634.ejournal.model.Student;
import com.cscb634.ejournal.model.Subject;
import com.cscb634.ejournal.service.student.StudentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(Constants.STUDENT_API)
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> create(@RequestBody Student student) {
        return ResponseEntity.of(Optional.of(studentService.create(student)));
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> update(@PathVariable UUID id, @RequestBody Student student) {
        return ResponseEntity.ok(studentService.update(id, student));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        studentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Student> get(@PathVariable UUID id) {
        return ResponseEntity.of(Optional.of(studentService.getById(id)));
    }

    @PostMapping(value = "/add/subject/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addSubject(@PathVariable UUID studentId, @RequestBody Subject subject) {
        studentService.addSubject(studentId, subject);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
