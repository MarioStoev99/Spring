package com.example.student.model;

import com.example.course.Course;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

//    @ManyToMany(
//            cascade = CascadeType.ALL
//    )
//    @JoinTable(name = "student_courses",
//            joinColumns = @JoinColumn(
//                    name = "student_id",
//                    referencedColumnName = "studentId"
//            ),
//            inverseJoinColumns = @JoinColumn(
//                    name = "course_id",
//                    referencedColumnName = "courseId"
//            ))
//    private List<Course> courses;

    @NotEmpty(message = "Name cannot be null or empty")
    private String name;

    @Min(value = 1, message = "Age should not be less than 1")
    @Max(value = 150, message = "Age should not be greater than 150")
    private int age;

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return age == student.age && Objects.equals(name, student.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
