package com.cscb634.ejournal.service.director;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testng.Assert;

import com.cscb634.ejournal.model.Director;
import com.cscb634.ejournal.repository.DirectorRepository;

@ExtendWith(MockitoExtension.class)
public class DirectorServiceTest {

    @Mock
    private DirectorRepository directorRepository;

    private DirectorService directorService;

    @BeforeEach
    void init() {
        directorService = new DirectorServiceImpl(directorRepository);
    }

    @Test
    public void test_create() {
        Director director = Director.builder()
                .address("test2")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test")
                .lastName("test1")
                .build();

        Mockito.when(directorRepository.saveAndFlush(any())).thenReturn(director);

        Director actual = directorService.create(director);

        Assert.assertEquals(actual.getFirstName(), "test");
        Assert.assertEquals(actual.getLastName(), "test1");
        Assert.assertEquals(actual.getAddress(), "test2");
        Assert.assertEquals(actual.getAge(), 30);

        verify(directorRepository, times(1)).saveAndFlush(any());
    }


    @Test
    public void test_update_directorNotExist() {
        Director director = Director.builder()
                .address("test2")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test")
                .lastName("test1")
                .build();

        Mockito.when(directorRepository.existsById(any())).thenReturn(false);

        Assert.assertThrows(IllegalStateException.class, () -> directorService.update(UUID.randomUUID(), director));
    }

    @Test
    public void test_update() {
        Director director = Director.builder()
                .address("test3")
                .id(UUID.randomUUID())
                .age(30)
                .firstName("test1")
                .lastName("test2")
                .build();

        Director updated = Director.builder()
                .address("updated3")
                .id(UUID.randomUUID())
                .age(40)
                .firstName("updated1")
                .lastName("updated2")
                .build();

        Mockito.when(directorRepository.existsById(any())).thenReturn(true);
        Mockito.when(directorRepository.saveAndFlush(any())).thenReturn(updated);

        Director actual = directorService.update(UUID.randomUUID(), updated);

        Assert.assertEquals(actual.getFirstName(), "updated1");
        Assert.assertEquals(actual.getLastName(), "updated2");
        Assert.assertEquals(actual.getAddress(), "updated3");
        Assert.assertEquals(actual.getAge(), 40);

        verify(directorRepository, times(1)).existsById(any());
        verify(directorRepository, times(1)).saveAndFlush(any());
    }

    @Test
    public void test_delete() {
        Mockito.when(directorRepository.existsById(any())).thenReturn(true);

        directorService.delete(UUID.randomUUID());

        verify(directorRepository, times(1)).existsById(any());
        verify(directorRepository, times(1)).deleteById(any());
    }

    @Test
    public void test_delete_directorNotExist() {
        Mockito.when(directorRepository.existsById(any())).thenReturn(false);

        Assert.assertThrows(IllegalStateException.class, () -> directorService.delete(UUID.randomUUID()));

        verify(directorRepository, times(1)).existsById(any());
        verify(directorRepository, times(0)).deleteById(any());
    }
}
