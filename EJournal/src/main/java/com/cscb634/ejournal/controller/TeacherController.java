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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cscb634.ejournal.constants.Constants;
import com.cscb634.ejournal.model.Teacher;
import com.cscb634.ejournal.service.student.StudentService;
import com.cscb634.ejournal.service.teacher.TeacherService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(Constants.TEACHER_API)
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;
    private final StudentService studentService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Teacher> create(@RequestBody Teacher teacher) {
        return ResponseEntity.of(Optional.of(teacherService.create(teacher)));
    }


    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Teacher> update(@PathVariable UUID id, @RequestBody Teacher teacher) {
        return ResponseEntity.ok(teacherService.update(id, teacher));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        teacherService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/add/{teacherId}/{studentId}/{schoolMark}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addSchoolMark(@PathVariable UUID teacherId, @PathVariable UUID studentId,
                                              @PathVariable int schoolMark) {
        studentService.addSchoolMark(teacherId, studentId, schoolMark);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/remove/{teacherId}/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> removeSchoolMark(@PathVariable UUID teacherId, @PathVariable UUID studentId) {
        studentService.removeSchoolMark(teacherId, studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/edit/{teacherId}/{studentId}/{schoolMark}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> editSchoolMark(@PathVariable UUID teacherId, @PathVariable UUID studentId,
                                               @PathVariable int schoolMark) {
        studentService.editSchoolMark(teacherId, studentId, schoolMark);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/register/{teacherId}/{studentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registerAbsence(@PathVariable UUID teacherId, @PathVariable UUID studentId) {
        studentService.registerAbsence(teacherId, studentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
