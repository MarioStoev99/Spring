package com.example.course.model;

import com.example.student.model.Student;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity(name = "courses")
public class Course {
    @Id
    private int id;
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "courses")
    private List<Student> students;

    public void enrollStudent(Student student) {
        students.add(student);
    }
}
