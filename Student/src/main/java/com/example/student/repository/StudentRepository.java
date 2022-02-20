package com.example.student.repository;

import com.example.student.model.Student;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface StudentRepository {
    void saveAndFlush(Student student);
    
    Optional<Student> getById(Long id);
    
    Optional<Student> deleteById(Long id);
    
    List<Student> findAll();
    
    Optional<Student> update(Student student);
}
