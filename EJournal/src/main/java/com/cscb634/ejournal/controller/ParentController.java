package com.cscb634.ejournal.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cscb634.ejournal.constants.Constants;
import com.cscb634.ejournal.model.Parent;
import com.cscb634.ejournal.model.Student;
import com.cscb634.ejournal.service.parent.ParentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping(Constants.PARENT_API)
@RequiredArgsConstructor
public class ParentController {

    private final ParentService parentService;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Parent> create(@RequestBody Parent parent) {
        return ResponseEntity.of(Optional.of(parentService.create(parent)));
    }

    @PostMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Parent> update(@PathVariable UUID id, @RequestBody Parent parent) {
        return ResponseEntity.ok(parentService.update(id, parent));
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        parentService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "/{parentId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Student>> getChildren(@PathVariable UUID parentId) {
        return ResponseEntity.ok(parentService.getChildren(parentId));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, value = "/{parentId}/{childId}")
    public ResponseEntity<Parent> addChild(@PathVariable UUID parentId, @PathVariable UUID childId) {
        return ResponseEntity.of(Optional.of(parentService.addChild(parentId, childId)));
    }
}
