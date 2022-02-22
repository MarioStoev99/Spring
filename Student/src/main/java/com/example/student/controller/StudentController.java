package com.example.student.controller;

import com.example.student.exception.StudentNotFoundException;
import com.example.student.model.Student;
import com.example.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentPostgreService;

    @Autowired
    public StudentController(StudentService studentPostgreService) {
        this.studentPostgreService = studentPostgreService;
    }

    @GetMapping
    public ResponseEntity<Collection<Student>> getStudents() {
        return new ResponseEntity<>(studentPostgreService.getStudents(), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @ResponseBody
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return new ResponseEntity<>(studentPostgreService.getStudent(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Collection<Student>> addStudent(@Valid @RequestBody Student student) {
        studentPostgreService.addStudent(student);
        return new ResponseEntity<>(studentPostgreService.getStudents(), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Collection<Student>> deleteStudent(@PathVariable Long id) {
        studentPostgreService.deleteStudent(id);
        return new ResponseEntity(studentPostgreService.getStudents(), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Collection<Student>> updateStudent(@PathVariable Long id, @Valid @RequestBody Student student) {
        studentPostgreService.updateStudent(id, student);
        return new ResponseEntity<>(studentPostgreService.getStudents(), HttpStatus.OK);
    }
}
