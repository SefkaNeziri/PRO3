package dk.via.pro3y.pro3semesterassignmentpart2.service;

import dk.via.pro3y.pro3semesterassignmentpart2.model.AnimalData;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@org.springframework.stereotype.Service
public class Service {

    private final Map<Integer, AnimalData> animalDataMap = new ConcurrentHashMap<>();

    public AnimalData addRegistrationNumber(int registrationNumber) {
        var animal = new AnimalData(registrationNumber);
        animalDataMap.put(registrationNumber, animal);

        return animal;
    }

    public AnimalData getAnimal(int registrationNumber) {
        return animalDataMap.get(registrationNumber);
    }
}