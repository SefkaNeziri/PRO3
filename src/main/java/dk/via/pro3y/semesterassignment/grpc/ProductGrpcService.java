package dk.via.pro3y.semesterassignment.grpc;

import dk.via.pro3y.semesterassignment.model.AnimalData;
import dk.via.pro3y.semesterassignment.service.ProductService;

import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductService productService;

    public ProductGrpcService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void getRegistrationNumbers(GetRegistrationNumbersRequest request, StreamObserver<AnimalListResponse> responseObserver) {

        int productId = request.getProductId();

        List<AnimalData> animals = productService.getAnimals(productId);

        AnimalListResponse.Builder response = AnimalListResponse.newBuilder();

        for (AnimalData animal : animals) {
            response.addAnimals(Animal.newBuilder().setRegistrationNumber(animal.getRegistrationNumber()).build());
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}