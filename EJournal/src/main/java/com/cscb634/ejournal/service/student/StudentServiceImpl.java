package com.cscb634.ejournal.service.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cscb634.ejournal.constants.ErrorMessageTemplate;
import com.cscb634.ejournal.model.Director;
import com.cscb634.ejournal.model.Student;
import com.cscb634.ejournal.model.Subject;
import com.cscb634.ejournal.model.Teacher;
import com.cscb634.ejournal.repository.DirectorRepository;
import com.cscb634.ejournal.repository.StudentRepository;
import com.cscb634.ejournal.repository.SubjectRepository;
import com.cscb634.ejournal.repository.TeacherRepository;
import com.cscb634.ejournal.util.spring.Utils;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final DirectorRepository directorRepository;

    @Override
    public Student create(Student student) {
        Optional<Student> dbStudent = studentRepository.findById(student.getId());
        if (dbStudent.isPresent()) {
            throw new IllegalArgumentException("The provided student already exist!");
        }

        return studentRepository.saveAndFlush(student);
    }

    @Override
    public Student update(UUID id, Student student) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalStateException("The provided id does not exist!");
        }

        return studentRepository.saveAndFlush(student);
    }

    @Override
    public Student getById(UUID id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalStateException("The provided id does not exist!");
        }
        return studentRepository.findById(id).get();
    }

    @Override
    public void delete(UUID id) {
        if (!studentRepository.existsById(id)) {
            throw new IllegalStateException("The provided id does not exist!");
        }

        studentRepository.deleteById(id);
    }

    @Override
    public void addSchoolMark(UUID teacherId, UUID studentId, int schoolMark) {
        Teacher teacher = getTeacher(teacherId);
        Student student = getStudent(studentId);

        List<Subject> subjects = student.getSubjects();
        for (Subject subject : subjects) {
            UUID subjectTeacherId = subject.getTeacher().getId();
            if (subjectTeacherId.equals(teacher.getId())) {
                List<Integer> grade = subject.getGrade();
                grade.add(schoolMark);
                subjectRepository.saveAndFlush(subject);
                return;
            }
        }
        throw new IllegalArgumentException(
                "The provided teacher does not exist or cannot modify datas for students which he's not teaching.");
    }

    @Override
    public void removeSchoolMark(UUID teacherId, UUID studentId) {
        Teacher teacher = getTeacher(teacherId);
        Student student = getStudent(studentId);

        List<Subject> subjects = student.getSubjects();
        for (Subject subject : subjects) {
            UUID subjectTeacherId = subject.getTeacher().getId();
            if (subjectTeacherId.equals(teacher.getId())) {
                List<Integer> grade = subject.getGrade();
                grade.remove(grade.size() - 1);
                subjectRepository.saveAndFlush(subject);
                return;
            }
        }
        throw new IllegalArgumentException(
                "The provided teacher does not exist or cannot modify datas for students which he's not teaching.");
    }

    @Override
    public void editSchoolMark(UUID teacherId, UUID studentId, int schoolMark) {
        removeSchoolMark(teacherId, studentId);
        addSchoolMark(teacherId, studentId, schoolMark);
    }

    @Override
    public void registerAbsence(UUID teacherId, UUID studentId) {
        Teacher teacher = getTeacher(teacherId);
        Student student = getStudent(studentId);

        List<Subject> subjects = student.getSubjects();
        for (Subject subject : subjects) {
            UUID subjectTeacherId = subject.getTeacher().getId();
            if (subjectTeacherId.equals(teacher.getId())) {
                subject.setAbsence(subject.getAbsence() + 1);
                subjectRepository.saveAndFlush(subject);
                return;
            }
        }
        throw new IllegalArgumentException(
                "The provided teacher does not exist or cannot modify datas for students which he's not teaching.");
    }

    @Override
    public Student addSubject(UUID studentId, Subject subject) {
        Student student = getStudent(studentId);
        List<Subject> subjects = student.getSubjects();
        for (Subject sub : subjects) {
            if (sub.getId().equals(subject.getId())) {
                throw new IllegalArgumentException("The provided subject already exist!");
            }
        }
        subjects.add(subject);
        return studentRepository.saveAndFlush(student);
    }

    @Override
    public List<Student> getStudents(UUID directorId) {
        Director director = getDirector(directorId);
        if (director == null) {
            throw new IllegalArgumentException("The provided id does not exist!");
        }
        return studentRepository.findAll();
    }

    private Teacher getTeacher(UUID teacherId) {
        return teacherRepository.findById(teacherId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(teacherId)));
    }

    private Student getStudent(UUID studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(studentId)));
    }


    private Director getDirector(UUID directorId) {
        return directorRepository.findById(directorId)
                .orElseThrow(() -> new EntityNotFoundException(
                        ErrorMessageTemplate.ENTITY_ID_DOES_NOT_EXIST.getFormattedMessage(directorId)));
    }
}
