package com.example.student;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.Map;

@Service
public class StudentService {

    private final Map<Long,Student> students;

    public StudentService() {
        this.students = new HashMap<>();
    }

    public Collection<Student> getStudents() {
        return students.values();
    }

    public void addStudent(Long id,Student student) {
        students.put(id,student);
    }

    public void deleteStudent(Long id) {
        students.remove(id);
    }

    public Student getStudent(Long id) {
        return students.get(id);
    }

    public void replaceStudent(Long id,Student student) {
        students.put(id,student);
    }
}
