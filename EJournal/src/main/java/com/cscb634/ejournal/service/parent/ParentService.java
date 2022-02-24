package com.cscb634.ejournal.service.parent;

import java.util.List;
import java.util.UUID;

import com.cscb634.ejournal.model.Parent;
import com.cscb634.ejournal.model.Student;

public interface ParentService {

    Parent create(Parent parent);

    Parent update(UUID id, Parent parent);

    void delete(UUID id);

    List<Student> getChildren(UUID parentId);

    Parent addChild(UUID parentId, UUID childId);

    List<Parent> getParents(UUID directorId);
}
