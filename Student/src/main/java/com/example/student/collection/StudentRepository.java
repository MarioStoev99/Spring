package com.example.student.collection;

import com.example.student.Student;

import java.util.Collection;
import java.util.Optional;


public interface StudentRepository {
    void add(Student student);
    
    Optional<Student> get(Long id);
    
    Optional<Student> remove(Long id);
    
    Collection<Student> getStudents();
    
    Optional<Student> update(Student student);
}
