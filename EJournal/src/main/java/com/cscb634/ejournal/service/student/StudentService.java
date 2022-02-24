package com.cscb634.ejournal.service.student;

import java.util.List;
import java.util.UUID;

import com.cscb634.ejournal.model.Student;
import com.cscb634.ejournal.model.Subject;

public interface  StudentService {

    Student create(Student student);

    Student update(UUID id, Student student);

    Student getById(UUID id);

    void delete(UUID id);

    void addSchoolMark(UUID teacherId, UUID studentId, int schoolMark);

    void removeSchoolMark(UUID teacherId, UUID studentId);

    void editSchoolMark(UUID teacherId, UUID studentId, int schoolMark);

    void registerAbsence(UUID teacherId, UUID studentId);

    Student addSubject(UUID studentId, Subject subject);

    List<Student> getStudents(UUID directorId);
}
