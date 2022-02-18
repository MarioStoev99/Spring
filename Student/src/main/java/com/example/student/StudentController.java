package com.example.student;

import com.example.student.Student;
import com.example.student.collection.StudentService;
import com.example.student.postgre.StudentPostgreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentPostgreService studentPostgreService;

    @Autowired
    public StudentController(StudentPostgreService studentPostgreService) {
        this.studentPostgreService = studentPostgreService;
    }

    @GetMapping
    public Collection<Student> getStudents() {
        return studentPostgreService.getStudents();
    }

    @GetMapping(value = "{id}")
    @ResponseBody
    public Student getStudents(@PathVariable Long id) {
        return studentPostgreService.getStudent(id);
    }

    @PostMapping
    public Collection<Student> addStudent(@RequestBody Student student) {
        studentPostgreService.addStudent(student);
        return studentPostgreService.getStudents();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public Collection<Student> deleteStudent(@PathVariable Long id) {
        studentPostgreService.deleteStudent(id);
        return studentPostgreService.getStudents();
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public Collection<Student> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        studentPostgreService.updateStudent(student);
        return studentPostgreService.getStudents();
    }
}
