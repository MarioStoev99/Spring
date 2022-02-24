package com.cscb634.ejournal.service.student;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.Assert;

import com.cscb634.ejournal.model.Director;
import com.cscb634.ejournal.model.Student;
import com.cscb634.ejournal.model.Subject;
import com.cscb634.ejournal.model.Teacher;
import com.cscb634.ejournal.repository.DirectorRepository;
import com.cscb634.ejournal.repository.StudentRepository;
import com.cscb634.ejournal.repository.SubjectRepository;
import com.cscb634.ejournal.repository.TeacherRepository;

import jakarta.persistence.EntityNotFoundException;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private SubjectRepository subjectRepository;

    @Mock
    private DirectorRepository directorRepository;

    private StudentService studentService;

    @BeforeEach
    void init() {
        studentService =
                new StudentServiceImpl(studentRepository, teacherRepository, subjectRepository, directorRepository);
    }

    @Test
    public void test_create() {
        Student student = Student.builder()
                .address("test2")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test")
                .lastName("test1")
                .build();

        Mockito.when(studentRepository.saveAndFlush(any())).thenReturn(student);

        Student actual = studentService.create(student);

        Assert.assertEquals(actual.getFirstName(), "test");
        Assert.assertEquals(actual.getLastName(), "test1");
        Assert.assertEquals(actual.getAddress(), "test2");
        Assert.assertEquals(actual.getAge(), 30);

        verify(studentRepository, times(1)).saveAndFlush(any());
    }

    @Test
    public void test_update_teacherNotExist() {
        Student student = Student.builder()
                .address("test2")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test")
                .lastName("test1")
                .build();

        Mockito.when(studentRepository.existsById(any())).thenReturn(false);

        Assert.assertThrows(IllegalStateException.class, () -> studentService.update(UUID.randomUUID(), student));
    }

    @Test
    public void test_update() {
        Student teacher = Student.builder()
                .address("test3")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test1")
                .lastName("test2")
                .build();

        Student updated = Student.builder()
                .address("updated3")
                .id(UUID.randomUUID())
                .age(40)
                .firstName("updated1")
                .lastName("updated2")
                .build();

        Mockito.when(studentRepository.existsById(any())).thenReturn(true);
        Mockito.when(studentRepository.saveAndFlush(any())).thenReturn(updated);

        Student actual = studentService.update(UUID.randomUUID(), updated);

        Assert.assertEquals(actual.getFirstName(), "updated1");
        Assert.assertEquals(actual.getLastName(), "updated2");
        Assert.assertEquals(actual.getAddress(), "updated3");
        Assert.assertEquals(actual.getAge(), 40);

        verify(studentRepository, times(1)).existsById(any());
        verify(studentRepository, times(1)).saveAndFlush(any());
    }

    @Test
    public void test_delete() {
        Mockito.when(studentRepository.existsById(any())).thenReturn(true);

        studentService.delete(UUID.randomUUID());

        verify(studentRepository, times(1)).existsById(any());
        verify(studentRepository, times(1)).deleteById(any());
    }

    @Test
    public void test_delete_directorNotExist() {
        Mockito.when(studentRepository.existsById(any())).thenReturn(false);

        Assert.assertThrows(IllegalStateException.class, () -> studentService.delete(UUID.randomUUID()));

        verify(studentRepository, times(1)).existsById(any());
        verify(studentRepository, times(0)).deleteById(any());
    }

    @Test
    public void test_getById_studentNotExist() {
        Mockito.when(studentRepository.existsById(any())).thenReturn(false);

        Assert.assertThrows(IllegalStateException.class, () -> studentService.getById(UUID.randomUUID()));

        verify(studentRepository, times(1)).existsById(any());
        verify(studentRepository, times(0)).findById(any());
    }

    @Test
    public void test_getById() {
        Student student = Student.builder()
                .address("test2")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test")
                .lastName("test1")
                .build();

        Mockito.when(studentRepository.existsById(any())).thenReturn(true);
        Mockito.when(studentRepository.findById(any())).thenReturn(Optional.ofNullable(student));

        Student actual = studentService.getById(UUID.randomUUID());

        Assert.assertEquals(actual.getFirstName(), "test");
        Assert.assertEquals(actual.getLastName(), "test1");
        Assert.assertEquals(actual.getAddress(), "test2");
        Assert.assertEquals(actual.getAge(), 30);

        verify(studentRepository, times(1)).existsById(any());
        verify(studentRepository, times(1)).findById(any());
    }

    @Test
    public void test_getStudents() {
        Student student = Student.builder()
                .address("test1")
                .build();

        Student student1 = Student.builder()
                .address("test2")
                .build();

        Mockito.when(directorRepository.findById(any())).thenReturn(Optional.ofNullable(Director.builder().build()));
        Mockito.when(studentRepository.findAll()).thenReturn(List.of(student, student1));

        List<Student> actualStudents = studentService.getStudents(UUID.randomUUID());

        Assert.assertEquals(actualStudents.size(), 2);

        verify(studentRepository, times(1)).findAll();
    }

    @Test
    public void test_getStudents_directorNotExist() {
        Mockito.when(directorRepository.findById(any())).thenReturn(Optional.empty());

        Assert.assertThrows(EntityNotFoundException.class, () -> studentService.getStudents(UUID.randomUUID()));

        verify(studentRepository, times(0)).findAll();
    }

    //@Test
    public void test_addSchoolMark() {
        UUID teacherId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        Teacher teacher = Teacher.builder()
                .address("test1")
                .id(teacherId)
                .build();

        Subject subject = Subject.builder()
                .teacher(teacher)
                .id(teacherId)
                .grade(List.of(4, 5))
                .absence(0)
                .name("Math")
                .build();

        Student student = Student.builder()
                .address("test1")
                .id(studentId)
                .subjects(List.of(subject))
                .build();

        Mockito.when(teacherRepository.findById(any())).thenReturn(Optional.of(teacher));
        Mockito.when(studentRepository.findById(any())).thenReturn(Optional.of(student));

        studentService.addSchoolMark(teacherId, studentId, 6);

        verify(subjectRepository, times(1)).saveAndFlush(any());
    }

    @Test
    public void test_addSchoolMark_teacherNotТeaching() {
        UUID teacherId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();

        Teacher teacher = Teacher.builder()
                .address("test1")
                .id(teacherId)
                .build();

        Subject subject = Subject.builder()
                .teacher(Teacher.builder().id(UUID.randomUUID()).build())
                .id(teacherId)
                .grade(List.of(4, 5))
                .absence(0)
                .name("Math")
                .build();

        Student student = Student.builder()
                .address("test1")
                .id(studentId)
                .subjects(List.of(subject))
                .build();

        Mockito.when(teacherRepository.findById(any())).thenReturn(Optional.of(teacher));
        Mockito.when(studentRepository.findById(any())).thenReturn(Optional.of(student));

        Assert.assertThrows(IllegalArgumentException.class,
                            () -> studentService.addSchoolMark(UUID.randomUUID(), studentId, 6));

        verify(subjectRepository, times(0)).saveAndFlush(any());
    }

    @Test
    public void test_addSchoolMark_studentNotExist() {
        UUID teacherId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        Teacher teacher = Teacher.builder()
                .address("test1")
                .id(teacherId)
                .build();

        Mockito.when(teacherRepository.findById(any())).thenReturn(Optional.of(teacher));
        Mockito.when(studentRepository.findById(any())).thenReturn(Optional.empty());

        Assert.assertThrows(EntityNotFoundException.class,
                            () -> studentService.addSchoolMark(UUID.randomUUID(), studentId, 6));

        verify(subjectRepository, times(0)).saveAndFlush(any());
    }

    @Test
    public void test_addSchoolMark_teacherNotExist() {
        Mockito.when(teacherRepository.findById(any())).thenReturn(Optional.empty());

        Assert.assertThrows(EntityNotFoundException.class,
                            () -> studentService.removeSchoolMark(UUID.randomUUID(), UUID.randomUUID()));

        verify(subjectRepository, times(0)).saveAndFlush(any());
    }

    @Test
    public void test_removeSchoolMark_studentNotExist() {
        UUID teacherId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        Teacher teacher = Teacher.builder()
                .address("test1")
                .id(teacherId)
                .build();

        Mockito.when(teacherRepository.findById(any())).thenReturn(Optional.of(teacher));
        Mockito.when(studentRepository.findById(any())).thenReturn(Optional.empty());

        Assert.assertThrows(EntityNotFoundException.class,
                            () -> studentService.removeSchoolMark(UUID.randomUUID(), studentId));

        verify(subjectRepository, times(0)).saveAndFlush(any());
    }

    @Test
    public void test_removeSchoolMark_teacherNotExist() {
        Mockito.when(teacherRepository.findById(any())).thenReturn(Optional.empty());

        Assert.assertThrows(EntityNotFoundException.class,
                            () -> studentService.removeSchoolMark(UUID.randomUUID(), UUID.randomUUID()));

        verify(subjectRepository, times(0)).saveAndFlush(any());
    }

    //@Test
    public void test_removeSchoolMark() {
        UUID teacherId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        Teacher teacher = Teacher.builder()
                .address("test1")
                .id(teacherId)
                .build();

        Subject subject = Subject.builder()
                .teacher(teacher)
                .id(teacherId)
                .grade(List.of(4, 5))
                .absence(0)
                .name("Math")
                .build();

        Student student = Student.builder()
                .address("test1")
                .id(studentId)
                .subjects(List.of(subject))
                .build();

        Mockito.when(teacherRepository.findById(any())).thenReturn(Optional.of(teacher));
        Mockito.when(studentRepository.findById(any())).thenReturn(Optional.of(student));

        studentService.removeSchoolMark(teacherId, studentId);

        verify(subjectRepository, times(1)).saveAndFlush(any());
    }

    @Test
    public void test_removeSchoolMark_teacherNotТeaching() {
        UUID teacherId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();

        Teacher teacher = Teacher.builder()
                .address("test1")
                .id(teacherId)
                .build();

        Subject subject = Subject.builder()
                .teacher(Teacher.builder().id(UUID.randomUUID()).build())
                .id(teacherId)
                .grade(List.of(4, 5))
                .absence(0)
                .name("Math")
                .build();

        Student student = Student.builder()
                .address("test1")
                .id(studentId)
                .subjects(List.of(subject))
                .build();

        Mockito.when(teacherRepository.findById(any())).thenReturn(Optional.of(teacher));
        Mockito.when(studentRepository.findById(any())).thenReturn(Optional.of(student));

        Assert.assertThrows(IllegalArgumentException.class,
                            () -> studentService.removeSchoolMark(UUID.randomUUID(), studentId));

        verify(subjectRepository, times(0)).saveAndFlush(any());
    }

    //@Test
    public void test_editSchoolMark() {
        UUID teacherId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        Teacher teacher = Teacher.builder()
                .address("test1")
                .id(teacherId)
                .build();

        Subject subject = Subject.builder()
                .teacher(teacher)
                .id(teacherId)
                .grade(List.of(4, 5))
                .absence(0)
                .name("Math")
                .build();

        Student student = Student.builder()
                .address("test1")
                .id(studentId)
                .subjects(List.of(subject))
                .build();

        Mockito.when(teacherRepository.findById(any())).thenReturn(Optional.of(teacher));
        Mockito.when(studentRepository.findById(any())).thenReturn(Optional.of(student));

        studentService.editSchoolMark(teacherId, studentId, 6);

        verify(subjectRepository, times(2)).saveAndFlush(any());
    }

    @Test
    public void test_registerAbsence() {
        UUID teacherId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        Teacher teacher = Teacher.builder()
                .address("test1")
                .id(teacherId)
                .build();

        Subject subject = Subject.builder()
                .teacher(teacher)
                .id(teacherId)
                .grade(List.of(4, 5))
                .absence(0)
                .name("Math")
                .build();

        Student student = Student.builder()
                .address("test1")
                .id(studentId)
                .subjects(List.of(subject))
                .build();

        Mockito.when(teacherRepository.findById(any())).thenReturn(Optional.of(teacher));
        Mockito.when(studentRepository.findById(any())).thenReturn(Optional.of(student));

        studentService.registerAbsence(teacherId, studentId);

        verify(subjectRepository, times(1)).saveAndFlush(any());
    }

    @Test
    public void test_registerAbsence_teacherNotТeaching() {
        UUID teacherId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();

        Teacher teacher = Teacher.builder()
                .address("test1")
                .id(teacherId)
                .build();

        Subject subject = Subject.builder()
                .teacher(Teacher.builder().id(UUID.randomUUID()).build())
                .id(teacherId)
                .grade(List.of(4, 5))
                .absence(0)
                .name("Math")
                .build();

        Student student = Student.builder()
                .address("test1")
                .id(studentId)
                .subjects(List.of(subject))
                .build();

        Mockito.when(teacherRepository.findById(any())).thenReturn(Optional.of(teacher));
        Mockito.when(studentRepository.findById(any())).thenReturn(Optional.of(student));

        Assert.assertThrows(IllegalArgumentException.class,
                            () -> studentService.registerAbsence(UUID.randomUUID(), studentId));

        verify(subjectRepository, times(0)).saveAndFlush(any());
    }

    @Test
    public void test_registerAbsence_studentNotExist() {
        UUID teacherId = UUID.randomUUID();
        UUID studentId = UUID.randomUUID();
        Teacher teacher = Teacher.builder()
                .address("test1")
                .id(teacherId)
                .build();

        Mockito.when(teacherRepository.findById(any())).thenReturn(Optional.of(teacher));
        Mockito.when(studentRepository.findById(any())).thenReturn(Optional.empty());

        Assert.assertThrows(EntityNotFoundException.class,
                            () -> studentService.registerAbsence(UUID.randomUUID(), studentId));

        verify(subjectRepository, times(0)).saveAndFlush(any());
    }

    @Test
    public void test_registerAbsence_teacherNotExist() {
        Mockito.when(teacherRepository.findById(any())).thenReturn(Optional.empty());

        Assert.assertThrows(EntityNotFoundException.class,
                            () -> studentService.registerAbsence(UUID.randomUUID(), UUID.randomUUID()));

        verify(subjectRepository, times(0)).saveAndFlush(any());
    }

    @Test
    public void test_addSubject_subjectExist() {
        Subject subject = Subject.builder()
                .teacher(Teacher.builder().id(UUID.randomUUID()).build())
                .grade(List.of(4, 5))
                .absence(0)
                .name("Math")
                .id(UUID.randomUUID())
                .build();

        Student student = Student.builder()
                .address("test1")
                .id(UUID.randomUUID())
                .subjects(List.of(subject))
                .build();

        Mockito.when(studentRepository.findById(any())).thenReturn(Optional.of(student));

        Assert.assertThrows(IllegalArgumentException.class,
                            () -> studentService.addSubject(UUID.randomUUID(), subject));

        verify(studentRepository, times(0)).saveAndFlush(any());
    }

    @Test
    public void test_addSubject_studentNotExist() {
        Subject subject = Subject.builder()
                .teacher(Teacher.builder().id(UUID.randomUUID()).build())
                .grade(List.of(4, 5))
                .absence(0)
                .name("Math")
                .build();
        Mockito.when(studentRepository.findById(any())).thenReturn(Optional.empty());

        Assert.assertThrows(EntityNotFoundException.class,
                            () -> studentService.addSubject(UUID.randomUUID(), subject));

        verify(studentRepository, times(0)).saveAndFlush(any());
    }
}
