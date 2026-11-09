package dk.via.pro3y.pro3semesterassignmentpart2.service;

public class AnimalNotFoundException extends RuntimeException {
    public AnimalNotFoundException(int registrationNumber) {
        super("Animal with registration number " + registrationNumber + " was not found");
    }
}
