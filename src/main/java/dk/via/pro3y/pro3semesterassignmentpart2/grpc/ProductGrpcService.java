package dk.via.pro3y.pro3semesterassignmentpart2.grpc;

import dk.via.pro3.grpc.Animal;
import dk.via.pro3.grpc.AnimalListResponse;
import dk.via.pro3.grpc.GetRegistrationNumbersRequest;
import dk.via.pro3.grpc.ProductServiceGrpc;
import dk.via.pro3y.pro3semesterassignmentpart2.model.AnimalData;
import dk.via.pro3y.pro3semesterassignmentpart2.service.ProductService;
import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
public class ProductGrpcService
        extends ProductServiceGrpc.ProductServiceImplBase {

    private final ProductService productService;

    public ProductGrpcService(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void getRegistrationNumbers(
            GetRegistrationNumbersRequest request,
            StreamObserver<AnimalListResponse> responseObserver) {

        int productId = request.getProductId();

        List<AnimalData> animals =
                productService.getAnimals(productId);

        AnimalListResponse.Builder response =
                AnimalListResponse.newBuilder();

        for (AnimalData animal : animals) {

            response.addAnimals(
                    Animal.newBuilder()
                            .setRegistrationNumber(
                                    animal.getRegistrationNumber()
                            )
                            .build()
            );
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}