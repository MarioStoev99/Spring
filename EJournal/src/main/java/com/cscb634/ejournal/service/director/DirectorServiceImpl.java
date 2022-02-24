package com.cscb634.ejournal.service.director;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.cscb634.ejournal.model.Director;
import com.cscb634.ejournal.repository.DirectorRepository;
import com.cscb634.ejournal.util.spring.Utils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DirectorServiceImpl implements DirectorService {

    private final DirectorRepository directorRepository;

    @Override
    public Director create(Director director) {
        return directorRepository.saveAndFlush(director);
    }

    @Override
    public Director update(UUID id, Director director) {
        if (!directorRepository.existsById(id)) {
            throw new IllegalStateException("The provided id does not exist!");
        }

        return directorRepository.saveAndFlush(director);
    }

    @Override
    public void delete(UUID id) {
        if (!directorRepository.existsById(id)) {
            throw new IllegalStateException("The provided id does not exist!");
        }

        directorRepository.deleteById(id);
    }


}
