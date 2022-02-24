package com.cscb634.ejournal.controller;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cscb634.ejournal.constants.Constants;
import com.cscb634.ejournal.model.Curriculum;
import com.cscb634.ejournal.service.curriculum.CurriculumService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(Constants.CURRICULUM_API)
@RequiredArgsConstructor
public class CurriculumController {

    private final CurriculumService curriculumService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Curriculum> create(@RequestBody Curriculum curriculum) {
        return ResponseEntity.of(Optional.of(curriculumService.create(curriculum)));
    }
}
