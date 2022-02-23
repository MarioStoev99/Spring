package com.example.course;

import com.example.course.Course;
import com.example.course.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Course getCourse(Long subjectId) {
        return courseRepository.findById(subjectId).get();
    }

    public Course saveCourse(Course course) {
        return courseRepository.saveAndFlush(course);
    }
}
