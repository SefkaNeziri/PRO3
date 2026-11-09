package dk.via.pro3y.pro3semesterassignmentpart2;

import dk.via.pro3y.pro3semesterassignmentpart2.model.AnimalRegistration;
import dk.via.pro3y.pro3semesterassignmentpart2.rest.AnimalRegistrationController;
import dk.via.pro3y.pro3semesterassignmentpart2.rest.CreateAnimalRequest;
import dk.via.pro3y.pro3semesterassignmentpart2.service.AnimalRegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AnimalRegistrationControllerTests {

    private AnimalRegistrationService service;
    private AnimalRegistrationController controller;

    @BeforeEach
    void setUp() {
        service = mock(AnimalRegistrationService.class);
        controller = new AnimalRegistrationController(service);
    }

    @Test
    void registerAnimalReturnsCreatedResponse() {
        CreateAnimalRequest request = new CreateAnimalRequest(
                202, LocalDate.of(2026, 11, 5), new BigDecimal("500.00"), "Farm D"
        );
        AnimalRegistration animal = new AnimalRegistration(
                202, request.date(), request.weight(), request.origin()
        );
        when(service.register(request)).thenReturn(animal);

        var response = controller.registerAnimal(request);

        assertEquals(201, response.getStatusCode().value());
        assertEquals("/api/animals/202", response.getHeaders().getLocation().toString());
        assertEquals(animal, response.getBody());
    }

    @Test
    void getAnimalsByDateUsesDateFilter() {
        LocalDate date = LocalDate.of(2026, 11, 5);
        when(service.getByDate(date)).thenReturn(List.of());

        assertTrue(controller.getAnimals(date, null).isEmpty());
        verify(service).getByDate(date);
        verify(service, never()).getByOrigin(anyString());
    }

    @Test
    void getAnimalsByOriginUsesOriginFilter() {
        when(service.getByOrigin("Farm A")).thenReturn(List.of());

        assertTrue(controller.getAnimals(null, "Farm A").isEmpty());
        verify(service).getByOrigin("Farm A");
        verify(service, never()).getByDate(any());
    }

    @Test
    void getAnimalsRequiresExactlyOneFilter() {
        assertThrows(IllegalArgumentException.class, () -> controller.getAnimals(null, null));
        assertThrows(IllegalArgumentException.class,
                () -> controller.getAnimals(LocalDate.now(), "Farm A"));
    }
}
