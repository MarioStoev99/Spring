package controller;

import com.example.student.exception.StudentNotFoundException;
import com.example.student.model.Student;
import com.example.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public ResponseEntity<Student> getStudents(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(studentPostgreService.getStudent(id), HttpStatus.OK);
        } catch (StudentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Collection<Student>> addStudent(@RequestBody Student student) {
        studentPostgreService.addStudent(student);
        return new ResponseEntity<>(studentPostgreService.getStudents(), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Collection<Student>> deleteStudent(@PathVariable Long id) {
        try {
            studentPostgreService.deleteStudent(id);
        } catch (StudentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
        return new ResponseEntity(studentPostgreService.getStudents(), HttpStatus.OK);
    }

    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
    public ResponseEntity<Collection<Student>> updateStudent(@PathVariable Long id, @RequestBody Student student) {
        try {
            studentPostgreService.updateStudent(id, student);
        } catch (StudentNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return new ResponseEntity<>(studentPostgreService.getStudents(), HttpStatus.OK);
    }
}
