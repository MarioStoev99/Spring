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
import com.cscb634.ejournal.model.Director;
import com.cscb634.ejournal.model.Parent;
import com.cscb634.ejournal.model.School;
import com.cscb634.ejournal.model.Student;
import com.cscb634.ejournal.model.Teacher;
import com.cscb634.ejournal.service.director.DirectorService;
import com.cscb634.ejournal.service.parent.ParentService;
import com.cscb634.ejournal.service.school.SchoolService;
import com.cscb634.ejournal.service.student.StudentService;
import com.cscb634.ejournal.service.teacher.TeacherService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(Constants.DIRECTOR_API)
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;
    private final ParentService parentService;
    private final SchoolService schoolService;
    private final TeacherService teacherService;
    private final StudentService studentService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Director> create(@RequestBody Director director) {
        return ResponseEntity.of(Optional.of(directorService.create(director)));
    }

    @PostMapping(value= "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Director> update(@PathVariable UUID id, @RequestBody Director director) {
        return ResponseEntity.ok(directorService.update(id, director));
    }

    @DeleteMapping(value= "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        directorService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/all/students/{directorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> getStudents(@PathVariable UUID directorId) {
        return ResponseEntity.ok(studentService.getStudents(directorId));
    }

    @GetMapping(value = "/all/parents/{directorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Parent>> getParents(@PathVariable UUID directorId) {
        return ResponseEntity.ok(parentService.getParents(directorId));
    }


    @GetMapping(value = "/all/schools/{directorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<School>> getSchools(@PathVariable UUID directorId) {
        return ResponseEntity.ok(schoolService.getSchools(directorId));
    }

    @GetMapping(value = "/all/teachers/{directorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Teacher>> getTeachers(@PathVariable UUID directorId) {
        return ResponseEntity.ok(teacherService.getTeachers(directorId));
    }
}
