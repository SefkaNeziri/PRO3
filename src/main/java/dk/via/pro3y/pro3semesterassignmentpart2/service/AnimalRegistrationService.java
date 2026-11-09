package dk.via.pro3y.pro3semesterassignmentpart2.service;

import dk.via.pro3y.pro3semesterassignmentpart2.model.AnimalRegistration;
import dk.via.pro3y.pro3semesterassignmentpart2.repository.AnimalRegistrationRepository;
import dk.via.pro3y.pro3semesterassignmentpart2.rest.CreateAnimalRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AnimalRegistrationService {

    private final AnimalRegistrationRepository repository;

    public AnimalRegistrationService(AnimalRegistrationRepository repository) {
        this.repository = repository;
    }

    public AnimalRegistration register(CreateAnimalRequest request) {
        AnimalRegistration animal = new AnimalRegistration(
                request.registrationNumber(),
                request.date(),
                request.weight(),
                request.origin().trim()
        );
        return repository.save(animal);
    }

    public AnimalRegistration getByRegistrationNumber(int registrationNumber) {
        return repository.findByRegistrationNumber(registrationNumber)
                .orElseThrow(() -> new AnimalNotFoundException(registrationNumber));
    }

    public List<AnimalRegistration> getByDate(LocalDate date) {
        return repository.findByDate(date);
    }

    public List<AnimalRegistration> getByOrigin(String origin) {
        return repository.findByOrigin(origin);
    }
}
