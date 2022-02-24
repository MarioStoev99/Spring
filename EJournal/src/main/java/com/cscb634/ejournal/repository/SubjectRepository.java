package com.cscb634.ejournal.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cscb634.ejournal.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, UUID> {

}
