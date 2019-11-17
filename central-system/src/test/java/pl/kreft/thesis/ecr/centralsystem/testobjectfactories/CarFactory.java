package pl.kreft.thesis.ecr.centralsystem.testobjectfactories;

import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.car.model.CarStatus;
import pl.kreft.thesis.ecr.centralsystem.car.model.CarType;

import java.time.Duration;
import java.time.Instant;

public class CarFactory {
    static public Car getCar() {
        return new Car(CarStatus.AVAILABLE,
                "Tigra",
                "Opel",
                CarType.CAR,
                Instant.now().minus(Duration.ofDays(500l)),
                200_000l, 20.0,
                Instant.now().minus(Duration.ofDays(200)),
                Instant.now().plus(Duration.ofDays(200)),
                Instant.now().plus(Duration.ofDays(200)),
                Instant.now(),
                false);
    }

    static public Car getPremiumCar() {
        return new Car(CarStatus.AVAILABLE,
                "BMW",
                "F10",
                CarType.PREMIUM_CAR,
                Instant.now().minus(Duration.ofDays(400l)),
                200_000l, 80.0,
                Instant.now().minus(Duration.ofDays(250)),
                Instant.now().plus(Duration.ofDays(150)),
                Instant.now().plus(Duration.ofDays(200)),
                Instant.now(),
                false);
    }
}
