package com.example.student;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Collection<Student> getStudents() {
        return studentService.getStudents();
    }

    @GetMapping(value = "{id}")
    @ResponseBody
    public Student getStudents(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @PostMapping
    public Collection<Student> addStudent(@RequestBody Student student) {
        studentService.addStudent(student.getId(), student);
        return studentService.getStudents();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Collection<Student> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return studentService.getStudents();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Collection<Student> replaceStudent(@PathVariable Long id, @RequestBody Student student) {
        studentService.replaceStudent(id, student);
        return studentService.getStudents();
    }
}
