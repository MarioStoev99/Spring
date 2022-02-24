package com.cscb634.ejournal.controller;

import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cscb634.ejournal.constants.Constants;
import com.cscb634.ejournal.model.School;
import com.cscb634.ejournal.model.Student;
import com.cscb634.ejournal.service.school.SchoolService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(Constants.SCHOOL_API)
@RequiredArgsConstructor
public class SchoolController {

    private final SchoolService schoolService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<School> create(@Valid @RequestBody School school) {
        return ResponseEntity.of(Optional.of(schoolService.create(school)));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/student/{schoolId}")
    public ResponseEntity<School> addStudent(@PathVariable UUID schoolId, @Valid @RequestBody Student student) {
        return ResponseEntity.of(Optional.of(schoolService.addStudent(schoolId, student)));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/student/{schoolId}/{studentId}")
    public ResponseEntity<School> removeStudent(@PathVariable UUID schoolId, @PathVariable UUID studentId) {
        return ResponseEntity.of(Optional.of(schoolService.removeStudent(schoolId, studentId)));
    }
}
