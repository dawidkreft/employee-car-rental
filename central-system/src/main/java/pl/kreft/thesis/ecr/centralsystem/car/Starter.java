package pl.kreft.thesis.ecr.centralsystem.car;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.car.model.CarStatus;
import pl.kreft.thesis.ecr.centralsystem.car.model.CarType;
import pl.kreft.thesis.ecr.centralsystem.car.repository.CarRepository;

import java.time.Duration;
import java.time.Instant;

@Component
public class Starter implements CommandLineRunner {

    @Autowired CarRepository carRepository;

    @Override public void run(String... args) throws Exception {
        carRepository.save(new Car(CarStatus.AVAILABLE,
                "Tigra",
                "Opel",
                CarType.CAR,
                Instant.now().minus(Duration.ofDays(500l)),
                200_000l, 20.0,
                Instant.now().minus(Duration.ofDays(200)),
                Instant.now().plus(Duration.ofDays(200)),
                Instant.now().plus(Duration.ofDays(200)),
                Instant.now(),
                false));
    }


}
