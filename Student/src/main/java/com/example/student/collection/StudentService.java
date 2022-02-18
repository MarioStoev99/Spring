package com.example.student.collection;

import com.example.student.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Collection<Student> getStudents() {
        return studentRepository.getStudents();
    }

    public void addStudent(Student student) {
        studentRepository.add(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.remove(id);
    }

    public Student getStudent(Long id) {
        return studentRepository.get(id).orElseThrow(() -> new IllegalStateException("This student does not exist!"));
    }

    public void updateStudent(Long id,Student student) {
        studentRepository.update(student);
    }

}
