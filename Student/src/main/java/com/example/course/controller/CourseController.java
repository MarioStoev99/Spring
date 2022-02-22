package com.example.course.controller;

import com.example.course.model.Course;
import com.example.course.service.CourseService;
import com.example.student.model.Student;
import com.example.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Collection;

@RestController
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private StudentService studentService;

    @GetMapping
    public ResponseEntity<Collection<Course>> getCourses() {
        return new ResponseEntity<>(courseService.getCourses(), HttpStatus.OK);
    }

    @PutMapping("/{subjectId}/students/{studentId}")
    public Course enrollStudentToCourse(@PathVariable Long subjectId,@PathVariable Long studentId) {
        Course course = courseService.getCourse(subjectId);
        Student student = studentService.getStudent(studentId);
        course.enrollStudent(student);
        return courseService.saveCourse(course);
    }
}
