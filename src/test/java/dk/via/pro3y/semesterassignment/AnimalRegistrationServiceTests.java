package dk.via.pro3y.semesterassignment;

import dk.via.pro3y.semesterassignment.model.AnimalRegistration;
import dk.via.pro3y.semesterassignment.repository.AnimalRegistrationRepository;
import dk.via.pro3y.semesterassignment.rest.CreateAnimalRequest;
import dk.via.pro3y.semesterassignment.service.AnimalNotFoundException;
import dk.via.pro3y.semesterassignment.service.AnimalRegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AnimalRegistrationServiceTests {

    @Mock
    private AnimalRegistrationRepository repository;

    private AnimalRegistrationService service;

    @BeforeEach
    void setUp() {
        service = new AnimalRegistrationService(repository);
    }

    @Test
    void registerStoresAllAnimalDetails() {
        CreateAnimalRequest request = new CreateAnimalRequest(
                201, LocalDate.of(2026, 11, 5), new BigDecimal("510.25"), " Farm C "
        );
        AnimalRegistration expected = new AnimalRegistration(
                201, LocalDate.of(2026, 11, 5), new BigDecimal("510.25"), "Farm C"
        );
        when(repository.save(any(AnimalRegistration.class))).thenReturn(expected);

        AnimalRegistration result = service.register(request);

        assertEquals(expected, result);
        verify(repository).save(expected);
    }

    @Test
    void getByRegistrationNumberReturnsAnimal() {
        AnimalRegistration expected = new AnimalRegistration(
                101, LocalDate.of(2026, 11, 3), new BigDecimal("425.50"), "Farm A"
        );
        when(repository.findByRegistrationNumber(101)).thenReturn(Optional.of(expected));

        assertEquals(expected, service.getByRegistrationNumber(101));
    }

    @Test
    void getByRegistrationNumberThrowsWhenAnimalDoesNotExist() {
        when(repository.findByRegistrationNumber(999)).thenReturn(Optional.empty());

        assertThrows(AnimalNotFoundException.class, () -> service.getByRegistrationNumber(999));
    }

    @Test
    void getByDateDelegatesToRepository() {
        LocalDate date = LocalDate.of(2026, 11, 3);
        when(repository.findByDate(date)).thenReturn(List.of());

        assertTrue(service.getByDate(date).isEmpty());
        verify(repository).findByDate(date);
    }

    @Test
    void getByOriginDelegatesToRepository() {
        when(repository.findByOrigin("Farm A")).thenReturn(List.of());

        assertTrue(service.getByOrigin("Farm A").isEmpty());
        verify(repository).findByOrigin("Farm A");
    }
}