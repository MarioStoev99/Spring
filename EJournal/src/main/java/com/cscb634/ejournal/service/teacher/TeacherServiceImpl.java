package com.cscb634.ejournal.service.teacher;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cscb634.ejournal.constants.ErrorMessageTemplate;
import com.cscb634.ejournal.model.Director;
import com.cscb634.ejournal.model.Teacher;
import com.cscb634.ejournal.repository.DirectorRepository;
import com.cscb634.ejournal.repository.TeacherRepository;
import com.cscb634.ejournal.util.spring.Utils;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;
    private final DirectorRepository directorRepository;

    @Override
    public Teacher create(Teacher teacher) {
        return teacherRepository.saveAndFlush(teacher);
    }

    @Override
    public Teacher update(UUID id, Teacher teacher) {
        if (!teacherRepository.existsById(id)) {
            throw new IllegalStateException("The provided id does not exist!");
        }

        return teacherRepository.saveAndFlush(teacher);
    }

    @Override
    public void delete(UUID id) {
        if (!teacherRepository.existsById(id)) {
            throw new IllegalStateException("The provided id does not exist!");
        }

        teacherRepository.deleteById(id);
    }

    @Override
    public List<Teacher> getTeachers(UUID directorId) {
        Director director = getDirector(directorId);
        if (director == null) {
            throw new IllegalArgumentException("The provided id does not exist!");
        }
        return teacherRepository.findAll();
    }

    private Director getDirector(UUID directorId) {
        return directorRepository.findById(directorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(directorId)));
    }
}
