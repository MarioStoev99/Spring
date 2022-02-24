package com.cscb634.ejournal.service.school;

import java.util.List;
import java.util.UUID;

import com.cscb634.ejournal.model.School;
import com.cscb634.ejournal.model.Student;

public interface SchoolService {

    School create(School school);

    School addStudent(UUID schoolId, Student student);

    School removeStudent(UUID schoolId, UUID studentId);

    List<School> getSchools(UUID directorId);
}
