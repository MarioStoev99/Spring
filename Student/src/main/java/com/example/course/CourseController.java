package com.example.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/course")
public class CourseController {

    private CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<Collection<Course>> getCourses() {
        return new ResponseEntity<>(courseService.getCourses(), HttpStatus.OK);
    }

    @GetMapping(value = "{id}")
    @ResponseBody
    public ResponseEntity<Course> getStudent(@PathVariable Long id) {
        return new ResponseEntity<>(courseService.getCourse(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Collection<Course>> addCourse(@Valid @RequestBody Course course) {
        courseService.addCourse(course);
        return new ResponseEntity<>(courseService.getCourses(), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Collection<Course>> deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
        return new ResponseEntity(courseService.getCourses(), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Collection<Course>> updateCourse(@PathVariable Long id, @RequestBody Course student) {
        courseService.updateCourse(id, student);
        return new ResponseEntity<>(courseService.getCourses(), HttpStatus.OK);
    }
}
