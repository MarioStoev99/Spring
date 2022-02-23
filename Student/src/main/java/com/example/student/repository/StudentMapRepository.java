package com.example.student.repository;

import com.example.student.model.Student;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class StudentMapRepository implements StudentRepository {

    private final Map<Long, Student> students;

    public StudentMapRepository() {
        students = new HashMap<>();
    }

    @Override
    public void saveAndFlush(Student student) {
        if(students.get(student.getStudentId()) != null) {
            throw new IllegalStateException("Student already exist!");
        }
        students.put(student.getStudentId(), student);
    }

    @Override
    public Optional<Student> getById(Long id) {
        return Optional.ofNullable(students.get(id));
    }

    @Override
    public Optional<Student> deleteById(Long id) {
        return Optional.ofNullable(students.remove(id));
    }

    @Override
    public List<Student> findAll() {
        return (List<Student>) students.values();
    }

    @Override
    public Optional<Student> update(Student student) {
        if(students.get(student.getStudentId()) == null) {
            throw new IllegalStateException("The provided student with id " + student.getStudentId() +  " does not exist!");
        }
        return Optional.ofNullable(students.put(student.getStudentId(),student));
    }
}
