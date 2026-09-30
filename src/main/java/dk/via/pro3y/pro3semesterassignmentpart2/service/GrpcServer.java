package dk.via.pro3y.pro3semesterassignmentpart2.service;

import dk.via.pro3.grpc.Animal;
import dk.via.pro3.grpc.AnimalDataResponse;
import dk.via.pro3.grpc.AnimalServiceGrpc;
import dk.via.pro3.grpc.GetRegistrationNumberRequest;
import dk.via.pro3y.pro3semesterassignmentpart2.model.AnimalData;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.grpc.server.service.GrpcService;

@GrpcService
public class GrpcServer extends AnimalServiceGrpc.AnimalServiceImplBase {

    private static final Logger log = LoggerFactory.getLogger(GrpcServer.class);

    private final Service service;

    public GrpcServer(Service service) {
        this.service = service;
    }

    @Override
    public void getRegistrationNumber(
            GetRegistrationNumberRequest req,
            StreamObserver<AnimalDataResponse> obs) {

        var animal = service.getAnimal(req.getRegistrationNumber());

        var res = AnimalDataResponse.newBuilder()
                .setAnimal(AnimalMapper.toProto(animal))
                .build();

        obs.onNext(res);
        obs.onCompleted();

        log.info("Animal data [{}] retrieved successfully", animal);
    }

    private static class AnimalMapper {

        static Animal toProto(AnimalData animalData) {
            return Animal.newBuilder()
                    .setRegistrationNumber(animalData.getRegistrationNumber())
                    .build();
        }
    }
}