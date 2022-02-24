package com.cscb634.ejournal.service.school;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cscb634.ejournal.constants.ErrorMessageTemplate;
import com.cscb634.ejournal.model.Director;
import com.cscb634.ejournal.model.School;
import com.cscb634.ejournal.model.Student;
import com.cscb634.ejournal.repository.DirectorRepository;
import com.cscb634.ejournal.repository.SchoolRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SchoolServiceImpl implements SchoolService {

    private final SchoolRepository schoolRepository;
    private final DirectorRepository directorRepository;

    @Override
    public School create(School school) {
        return schoolRepository.saveAndFlush(school);
    }

    @Override
    public School addStudent(UUID schoolId, Student student) {
        School school = getSchool(schoolId);

        List<Student> students = school.getStudents();
        for (Student databaseStudent : students) {
            if (databaseStudent.getId().equals(student.getId())) {
                throw new IllegalArgumentException("The provided id already exist.");
            }
        }
        students.add(student);
        student.setSchool(school);

        return schoolRepository.saveAndFlush(school);
    }

    @Override
    public School removeStudent(UUID schoolId, UUID studentId) {
        School school = getSchool(schoolId);
        List<Student> students = school.getStudents();
        for (Student student : students) {
            if (student.getId().equals(studentId)) {
                students.remove(student);
                student.setSchool(null);
                break;
            }
        }
        return schoolRepository.saveAndFlush(school);
    }

    @Override
    public List<School> getSchools(UUID directorId) {
        Director director = getDirector(directorId);
        if (director == null) {
            throw new IllegalArgumentException("The provided id does not exist!");
        }
        return schoolRepository.findAll();
    }

    private School getSchool(UUID schoolId) {
        return schoolRepository.findById(schoolId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(schoolId)));
    }

    private Director getDirector(UUID directorId) {
        return directorRepository.findById(directorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(directorId)));
    }
}
