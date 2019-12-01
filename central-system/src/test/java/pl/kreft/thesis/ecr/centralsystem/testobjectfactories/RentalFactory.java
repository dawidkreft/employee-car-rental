package pl.kreft.thesis.ecr.centralsystem.testobjectfactories;

import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.rental.model.Rental;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;

import java.time.Instant;
import java.time.Period;

public class RentalFactory {
    static public Rental getRandomRental(User lender, Car car) {
        return new Rental(
                lender,
                car,
                Instant.now(),
                "wyjazd",
                Instant.now().plus(Period.ofDays(1)),
                Instant.now().plus(Period.ofDays(3)),
                null,
                null,
                true,
                true,
                null,
                false,
                null,
                Instant.now(),
                false
        );
    }
}
