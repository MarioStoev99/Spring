package com.example.course.service;

import com.example.course.model.Course;
import com.example.course.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
