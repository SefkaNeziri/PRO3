package dk.via.pro3y.pro3semesterassignmentpart2.service;

import dk.via.pro3y.pro3semesterassignmentpart2.model.AnimalData;
import dk.via.pro3y.pro3semesterassignmentpart2.repository.AnimalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final AnimalRepository animalRepository;

    public ProductService(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    public List<AnimalData> getAnimals(int productId) {
        return animalRepository.findByProductId(productId);
    }
}