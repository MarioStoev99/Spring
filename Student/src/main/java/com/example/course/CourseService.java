package com.example.course;

import com.example.course.exception.CourseNotFoundException;
import com.example.student.exception.StudentNotFoundException;
import com.example.student.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CourseService {

    private static final String COURSE_NOT_FOUND = "Course with provided id does not exist!";
    private static final String INVALID_FIELD = "Invalid course field!";

    private CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> getCourses() {
        return courseRepository.findAll();
    }

    public Course getCourse(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new CourseNotFoundException(COURSE_NOT_FOUND));
    }

    public List<Course> addCourse(Course course) {
        courseRepository.saveAndFlush(course);
        return courseRepository.findAll();
    }

    public List<Course> deleteCourse(Long id) {
        Optional<Course> student = courseRepository.findById(id);
        if (student.isEmpty()) {
            throw new CourseNotFoundException(COURSE_NOT_FOUND);
        }
        courseRepository.deleteById(id);
        return courseRepository.findAll();
    }

    @Transactional
    public List<Course> updateCourse(Long id, Course course) {
        Optional<Course> databaseCourse = courseRepository.findById(id);
        if (databaseCourse.isEmpty()) {
            throw new CourseNotFoundException("The provided id does not exist!");
        }

        Course databaseCourseObject = databaseCourse.get();
        if (Objects.equals(databaseCourseObject.getName(), course.getName())) {
            throw new IllegalArgumentException(INVALID_FIELD);
        } else {
            databaseCourseObject.setName(course.getName());
        }

        return courseRepository.findAll();
    }

}
