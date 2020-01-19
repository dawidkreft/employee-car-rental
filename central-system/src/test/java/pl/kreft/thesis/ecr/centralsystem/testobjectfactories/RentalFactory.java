package pl.kreft.thesis.ecr.centralsystem.testobjectfactories;

import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.rental.model.CarRentalRequest;
import pl.kreft.thesis.ecr.centralsystem.rental.model.Rental;
import pl.kreft.thesis.ecr.centralsystem.rental.model.ReturnCarRequest;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.UUID;

public class RentalFactory {

    public static Long testCarDistance = 500l;
    public static Long testCurrentCarKilometer = 250_000l;
    public static double testFuelLevel = 40.5;

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
                null,
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

    static public CarRentalRequest prepareRequest(UUID userId, UUID carId){
        return new CarRentalRequest(
                carId,
                userId,
                LocalDateTime.now().plus(Duration.ofDays(2)),
                LocalDateTime.now().plus(Duration.ofDays(4)),
                "Podróż służbowa"
        );
    }

    static public ReturnCarRequest prepareReturnRequest(UUID rentalId){
        return new ReturnCarRequest(
                rentalId,
                Instant.now().plus(Duration.ofDays(2)),
                Instant.now().plus(Duration.ofDays(4)),
                "OK",
                testCarDistance,
                testCurrentCarKilometer,
                "OK",
                testFuelLevel

        );
    }
}
