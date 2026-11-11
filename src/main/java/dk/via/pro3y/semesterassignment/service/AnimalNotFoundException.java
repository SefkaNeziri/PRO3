package dk.via.pro3y.semesterassignment.service;

public class AnimalNotFoundException extends RuntimeException {
    public AnimalNotFoundException(int registrationNumber) {
        super("Animal with registration number " + registrationNumber + " was not found");
    }
}