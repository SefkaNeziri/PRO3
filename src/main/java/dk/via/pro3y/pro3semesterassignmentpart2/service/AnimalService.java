package dk.via.pro3y.pro3semesterassignmentpart2.service;

import dk.via.pro3y.pro3semesterassignmentpart2.model.ProductData;
import dk.via.pro3y.pro3semesterassignmentpart2.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnimalService {

    private final ProductRepository productRepository;

    public AnimalService(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    public List<ProductData> getProducts(int registrationNumber) {
        return productRepository.findByAnimalRegistrationNumber(registrationNumber);
    }
}
