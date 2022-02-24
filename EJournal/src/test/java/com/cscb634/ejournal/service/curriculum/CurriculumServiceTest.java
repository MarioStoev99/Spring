package com.cscb634.ejournal.service.curriculum;

import static org.mockito.ArgumentMatchers.any;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.cscb634.ejournal.model.Curriculum;
import com.cscb634.ejournal.model.Subject;
import com.cscb634.ejournal.model.Teacher;
import com.cscb634.ejournal.repository.CurriculumRepository;

@ExtendWith(MockitoExtension.class)
class CurriculumServiceTest {

    @Mock
    private CurriculumRepository curriculumRepository;

    private CurriculumService curriculumService;

    @BeforeEach
    void init() {
        curriculumService = new CurriculumServiceImpl(curriculumRepository);
    }

    @Test
    void test_create() {
        Curriculum curriculum = Curriculum.builder()
                .id(UUID.randomUUID())
                .term(1)
                .teachers(List.of(Teacher.builder().build()))
                .subjects(List.of(Subject.builder().build()))
                .build();

        curriculumService.create(curriculum);

        Mockito.verify(curriculumRepository, Mockito.times(1)).saveAndFlush(any());
    }

}
