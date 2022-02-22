package com.example.student.service;

import com.example.student.exception.StudentNotFoundException;
import com.example.student.model.Student;
import com.example.student.repository.StudentPostgreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StudentService {

    private static final String STUDENT_NOT_FOUND = "Student with provided id does not exist!";
    private static final String INVALID_FIELD = "Invalid student field!";

    private final StudentPostgreRepository studentPostgreRepository;

    @Autowired
    public StudentService(StudentPostgreRepository studentPostgreRepository) {
        this.studentPostgreRepository = studentPostgreRepository;
    }

    public List<Student> getStudents() {
        return studentPostgreRepository.findAll();
    }

    public Student getStudent(Long id) throws StudentNotFoundException {
        return studentPostgreRepository.findById(id).orElseThrow(() -> new StudentNotFoundException(STUDENT_NOT_FOUND));
    }

    public List<Student> addStudent(Student student) {
        studentPostgreRepository.saveAndFlush(student);
        return studentPostgreRepository.findAll();
    }

    public List<Student> deleteStudent(Long id) throws StudentNotFoundException {
        Optional<Student> student = studentPostgreRepository.findById(id);
        if (student.isEmpty()) {
            throw new StudentNotFoundException(STUDENT_NOT_FOUND);
        }
        studentPostgreRepository.deleteById(id);
        return studentPostgreRepository.findAll();
    }

    @Transactional
    public List<Student> updateStudent(Long id, Student student) throws StudentNotFoundException {
        Optional<Student> databaseStudent = studentPostgreRepository.findById(id);
        if (databaseStudent.isEmpty()) {
            throw new StudentNotFoundException("The provided id does not exist!");
        }

        Student databaseStudentObject = databaseStudent.get();
        if (Objects.equals(databaseStudentObject.getName(), student.getName())) {
            throw new IllegalArgumentException(INVALID_FIELD);
        } else {
            databaseStudentObject.setAge(student.getAge());
            databaseStudentObject.setName(student.getName());
        }

        return studentPostgreRepository.findAll();
    }


}
