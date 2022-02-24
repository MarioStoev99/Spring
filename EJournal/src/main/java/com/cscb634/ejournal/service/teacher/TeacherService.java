package com.cscb634.ejournal.service.teacher;

import java.util.List;
import java.util.UUID;

import com.cscb634.ejournal.model.Teacher;

public interface TeacherService {

    Teacher create(Teacher teacher);

    Teacher update(UUID id, Teacher teacher);

    void delete(UUID id);

    List<Teacher> getTeachers(UUID directorId);
}
