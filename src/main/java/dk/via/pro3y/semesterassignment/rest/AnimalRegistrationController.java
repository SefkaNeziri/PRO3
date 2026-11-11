package dk.via.pro3y.semesterassignment.rest;

import dk.via.pro3y.semesterassignment.model.AnimalRegistration;
import dk.via.pro3y.semesterassignment.service.AnimalRegistrationService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/animals")
public class AnimalRegistrationController {

    private final AnimalRegistrationService service;

    public AnimalRegistrationController(AnimalRegistrationService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<AnimalRegistration> registerAnimal(
            @Valid @RequestBody CreateAnimalRequest request) {
        AnimalRegistration created = service.register(request);
        URI location = URI.create("/api/animals/" + created.registrationNumber());
        return ResponseEntity.created(location).body(created);
    }

    @GetMapping("/{registrationNumber}")
    public AnimalRegistration getAnimal(@PathVariable int registrationNumber) {
        return service.getByRegistrationNumber(registrationNumber);
    }

    @GetMapping
    public List<AnimalRegistration> getAnimals(
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(required = false) String origin) {

        boolean hasDate = date != null;
        boolean hasOrigin = origin != null && !origin.isBlank();

        if (hasDate == hasOrigin) {
            throw new IllegalArgumentException(
                    "Provide exactly one filter: either 'date' (YYYY-MM-DD) or 'origin'.");
        }
        return hasDate ? service.getByDate(date) : service.getByOrigin(origin);
    }
}