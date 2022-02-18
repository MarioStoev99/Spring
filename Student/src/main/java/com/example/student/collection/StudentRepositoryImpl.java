package com.example.student.collection;

import com.example.student.Student;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class StudentRepositoryImpl implements StudentRepository {

    private final Map<Long, Student> students;

    public StudentRepositoryImpl() {
        students = new HashMap<>();
    }

    @Override
    public void add(Student student) {
        if(students.get(student.getId()) != null) {
            throw new IllegalStateException("Student already exist!");
        }
        students.put(student.getId(), student);
    }

    @Override
    public Optional<Student> get(Long id) {
        return Optional.ofNullable(students.get(id));
    }

    @Override
    public Optional<Student> remove(Long id) {
        return Optional.ofNullable(students.remove(id));
    }

    @Override
    public Collection<Student> getStudents() {
        return students.values();
    }

    @Override
    public Optional<Student> update(Student student) {
        if(students.get(student.getId()) == null) {
            throw new IllegalStateException("The provided student with id " + student.getId() +  " does not exist!");
        }
        return Optional.ofNullable(students.put(student.getId(),student));
    }
}
