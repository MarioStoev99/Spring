package com.example.student.postgre;

import com.example.student.Student;
import com.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentPostgreService {

    private final StudentPostgreRepository studentPostgreRepository;

    @Autowired
    public StudentPostgreService(StudentPostgreRepository studentPostgreRepository) {
        this.studentPostgreRepository = studentPostgreRepository;
    }

    public List<Student> getStudents() {
       return studentPostgreRepository.findAll();
    }

    public Student getStudent(Long id) {
        return studentPostgreRepository.getById(id);
    }

    public List<Student> addStudent(Student student) {
       studentPostgreRepository.saveAndFlush(student);
       return studentPostgreRepository.findAll();
    }

    public List<Student> deleteStudent(Long id) {
        studentPostgreRepository.deleteById(id);
        return studentPostgreRepository.findAll();
    }

    public List<Student> updateStudent(Student student) {
        Optional<Student> databaseStudent = studentPostgreRepository.findById(student.getId());
        BeanUtils.copyProperties(student,databaseStudent,"id");
        //studentPostgreRepository.saveAndFlush(databaseStudent);
        return studentPostgreRepository.findAll();
    }
}
