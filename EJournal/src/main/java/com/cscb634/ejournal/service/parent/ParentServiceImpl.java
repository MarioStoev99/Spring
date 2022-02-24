package com.cscb634.ejournal.service.parent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cscb634.ejournal.constants.ErrorMessageTemplate;
import com.cscb634.ejournal.model.Director;
import com.cscb634.ejournal.model.Parent;
import com.cscb634.ejournal.model.Student;
import com.cscb634.ejournal.repository.DirectorRepository;
import com.cscb634.ejournal.repository.ParentRepository;
import com.cscb634.ejournal.repository.StudentRepository;
import com.cscb634.ejournal.util.spring.Utils;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ParentServiceImpl implements ParentService {

    private final ParentRepository parentRepository;
    private final StudentRepository studentRepository;
    private final DirectorRepository directorRepository;

    @Override
    public Parent create(Parent parent) {
        UUID parentId = parent.getId();
        Optional<Parent> databaseParent = parentRepository.findById(parentId);
        if (databaseParent.isPresent()) {
            throw new IllegalArgumentException(
                    ErrorMessageTemplate.ENTITY_ID_ALREADY_EXIST.getFormattedMessage(parentId));
        }

        return parentRepository.saveAndFlush(parent);
    }

    @Override
    public Parent update(UUID id, Parent parent) {
        if (!parentRepository.existsById(id)) {
            throw new IllegalStateException("The provided id does not exist!");
        }

        return parentRepository.saveAndFlush(parent);
    }

    @Override
    public void delete(UUID id) {
        if (!parentRepository.existsById(id)) {
            throw new IllegalStateException("The provided id does not exist!");
        }

        parentRepository.deleteById(id);
    }

    @Override
    public List<Student> getChildren(UUID parentId) {
        Parent parent = getParent(parentId);

        return parent.getStudents();
    }

    @Override
    public Parent addChild(UUID parentId, UUID childId) {
        Student student = getChild(childId);
        Parent parent = getParent(parentId);
        List<Student> students = parent.getStudents();
        students.add(student);
        student.setParent(parent);
        parentRepository.save(parent);
        return parent;
    }

    @Override
    public List<Parent> getParents(UUID directorId) {
        Director director = getDirector(directorId);
        if (director == null) {
            throw new IllegalArgumentException("The provided id does not exist!");
        }
        return parentRepository.findAll();
    }

    private Parent getParent(UUID parentId) {
        return parentRepository.findById(parentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(parentId)));
    }

    private Student getChild(UUID childId) {
        return studentRepository.findById(childId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(childId)));
    }

    private Director getDirector(UUID directorId) {
        return directorRepository.findById(directorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(directorId)));
    }
}
