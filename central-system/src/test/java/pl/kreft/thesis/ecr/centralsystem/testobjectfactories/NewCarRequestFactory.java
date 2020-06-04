package pl.kreft.thesis.ecr.centralsystem.testobjectfactories;

import pl.kreft.thesis.ecr.centralsystem.car.model.CarType;
import pl.kreft.thesis.ecr.centralsystem.car.model.NewCarRequest;

import java.time.LocalDate;

public class NewCarRequestFactory {
    public static NewCarRequest getNewCarRequest() {
        return NewCarRequest.builder()
                            .model("Astra")
                            .brand("Tigra")
                            .type(CarType.CAR)
                            .dateOfNextTechnicalExamination(LocalDate.now())
                            .dateOfNextReview(LocalDate.now())
                            .dateOfLastReview(LocalDate.now())
                            .build();
    }

    public static NewCarRequest getNewCarRequestForPremiumCar() {
        return NewCarRequest.builder()
                            .model("F10")
                            .brand("BMW")
                            .type(CarType.PREMIUM_CAR)
                            .dateOfNextTechnicalExamination(LocalDate.now())
                            .dateOfNextReview(LocalDate.now())
                            .dateOfLastReview(LocalDate.now())
                            .build();
    }
}
