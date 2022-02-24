package com.cscb634.ejournal.service.director;

import java.util.UUID;

import com.cscb634.ejournal.model.Director;

public interface DirectorService {

    Director create(Director director);

    Director update(UUID id, Director director);

    void delete(UUID id);
}
