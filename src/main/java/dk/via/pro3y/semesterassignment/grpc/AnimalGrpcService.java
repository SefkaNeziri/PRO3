package dk.via.pro3y.semesterassignment.grpc;

import dk.via.pro3y.semesterassignment.model.ProductData;
import dk.via.pro3y.semesterassignment.service.AnimalService;

import io.grpc.stub.StreamObserver;
import org.springframework.grpc.server.service.GrpcService;

import java.util.List;

@GrpcService
public class AnimalGrpcService extends AnimalServiceGrpc.AnimalServiceImplBase {

    private final AnimalService animalService;

    public AnimalGrpcService(AnimalService animalService) {
        this.animalService = animalService;
    }

    @Override
    public void getProducts(GetProductsRequest request, StreamObserver<ProductListResponse> responseObserver) {

        int registrationNumber = request.getRegistrationNumber();

        List<ProductData> products = animalService.getProducts(registrationNumber);

        ProductListResponse.Builder response = ProductListResponse.newBuilder();

        for (ProductData product : products) {
            Product.Builder productBuilder = Product.newBuilder().setProductId(product.getProductId());

            for (int registrationNumberInProduct : product.getRegistrationNumbers()) {
                productBuilder.addRegistrationNumbers(registrationNumberInProduct);
            }

            response.addProducts(productBuilder.build());
        }

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }
}
