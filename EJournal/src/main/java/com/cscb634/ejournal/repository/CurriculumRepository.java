package com.cscb634.ejournal.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cscb634.ejournal.model.Curriculum;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, UUID> {

}
