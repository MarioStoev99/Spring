package com.example.course;

import com.example.course.material.CourseMaterial;
import com.example.student.model.Student;
import com.example.teacher.Teacher;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ManyToAny;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;
    private String name;

//    @OneToOne(
//            mappedBy = "courses"
//    )
//    private CourseMaterial courseMaterial;
//
//    @ManyToOne(
//            // The object will be created and saved automatically to the database,so we will bypass exceptions
//            cascade = CascadeType.ALL
//    )
//    @JoinColumn(
//            name = "teacher_id",
//            referencedColumnName = "teacherId"
//    )
//    private Teacher teacher;


//    @ManyToMany(mappedBy = "courses")
//    private List<Student> students;
}
