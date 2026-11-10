package dk.via.pro3y.semesterassignment.service;

import dk.via.pro3y.semesterassignment.model.ProductData;
import dk.via.pro3y.semesterassignment.repository.ProductRepository;
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
