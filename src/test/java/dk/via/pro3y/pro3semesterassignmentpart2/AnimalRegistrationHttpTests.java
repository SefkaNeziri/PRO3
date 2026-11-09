package dk.via.pro3y.pro3semesterassignmentpart2;

import dk.via.pro3y.pro3semesterassignmentpart2.model.AnimalRegistration;
import dk.via.pro3y.pro3semesterassignmentpart2.rest.AnimalRegistrationController;
import dk.via.pro3y.pro3semesterassignmentpart2.exception.RestExceptionHandler;
import dk.via.pro3y.pro3semesterassignmentpart2.service.AnimalRegistrationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class AnimalRegistrationHttpTests {

    private AnimalRegistrationService service;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        service = mock(AnimalRegistrationService.class);
        AnimalRegistrationController controller = new AnimalRegistrationController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new RestExceptionHandler())
                .build();
    }

    @Test
    void postRegistersAnimalAndReturns201() throws Exception {
        AnimalRegistration animal = new AnimalRegistration(
                301, LocalDate.of(2026, 11, 5), new BigDecimal("520.00"), "Farm E"
        );
        when(service.register(any())).thenReturn(animal);

        mockMvc.perform(post("/api/animals")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "registrationNumber": 301,
                                  "date": "2026-11-05",
                                  "weight": 520.00,
                                  "origin": "Farm E"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/animals/301"))
                .andExpect(jsonPath("$.registrationNumber").value(301))
                .andExpect(jsonPath("$.origin").value("Farm E"));
    }

    @Test
    void getAnimalByRegistrationNumberReturnsAnimal() throws Exception {
        AnimalRegistration animal = new AnimalRegistration(
                101, LocalDate.of(2026, 11, 3), new BigDecimal("425.50"), "Farm A"
        );
        when(service.getByRegistrationNumber(101)).thenReturn(animal);

        mockMvc.perform(get("/api/animals/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.registrationNumber").value(101));
    }

    @Test
    void getAnimalsByDateReturnsList() throws Exception {
        LocalDate date = LocalDate.of(2026, 11, 3);
        when(service.getByDate(date)).thenReturn(List.of());

        mockMvc.perform(get("/api/animals").param("date", "2026-11-03"))
                .andExpect(status().isOk());

        verify(service).getByDate(date);
    }

    @Test
    void getAnimalsByOriginReturnsList() throws Exception {
        when(service.getByOrigin("Farm A")).thenReturn(List.of());

        mockMvc.perform(get("/api/animals").param("origin", "Farm A"))
                .andExpect(status().isOk());

        verify(service).getByOrigin("Farm A");
    }

    @Test
    void listingWithoutFilterReturns400() throws Exception {
        mockMvc.perform(get("/api/animals"))
                .andExpect(status().isBadRequest());
    }
}
