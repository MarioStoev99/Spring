package com.cscb634.ejournal.service.curriculum;

import java.util.List;

import org.springframework.stereotype.Service;

import com.cscb634.ejournal.model.Curriculum;
import com.cscb634.ejournal.model.Subject;
import com.cscb634.ejournal.model.Teacher;
import com.cscb634.ejournal.repository.CurriculumRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CurriculumServiceImpl implements CurriculumService {

    private final CurriculumRepository curriculumRepository;

    @Override
    public Curriculum create(Curriculum curriculum) {
        List<Subject> subjects = curriculum.getSubjects();
        List<Teacher> teachers = curriculum.getTeachers();
        for (int i = 0; i < subjects.size(); ++i) {
            Teacher teacher = teachers.get(i);
            Subject subject = subjects.get(i);
            subject.setTeacher(teacher);
            teacher.setSubject(subject);
        }
        return curriculumRepository.saveAndFlush(curriculum);
    }
}
