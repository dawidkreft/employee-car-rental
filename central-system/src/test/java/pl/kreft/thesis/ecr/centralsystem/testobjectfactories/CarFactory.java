package pl.kreft.thesis.ecr.centralsystem.testobjectfactories;

import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.car.model.CarStatus;
import pl.kreft.thesis.ecr.centralsystem.car.model.CarType;

import java.time.Instant;
import java.time.LocalDate;

public class CarFactory {
    static public Car getCar() {

        return Car.builder()
                  .model("Astra")
                  .brand("Tigra")
                  .type(CarType.CAR)
                  .productionYear(2020)
                  .status(CarStatus.AVAILABLE)
                  .dateOfNextTechnicalExamination(LocalDate.now())
                  .dateOfNextReview(LocalDate.now())
                  .dateOfLastReview(LocalDate.now())
                  .creationDate(Instant.now())
                  .removed(false)
                  .build();
    }

    static public Car getPremiumCar() {
        return Car.builder()
                  .model("F10")
                  .brand("BMW")
                  .type(CarType.PREMIUM_CAR)
                  .productionYear(2020)
                  .status(CarStatus.AVAILABLE)
                  .dateOfNextTechnicalExamination(LocalDate.now())
                  .dateOfNextReview(LocalDate.now())
                  .dateOfLastReview(LocalDate.now())
                  .creationDate(Instant.now())
                  .removed(false)
                  .build();
    }
}
