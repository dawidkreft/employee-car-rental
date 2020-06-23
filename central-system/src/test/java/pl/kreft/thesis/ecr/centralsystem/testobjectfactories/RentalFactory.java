package pl.kreft.thesis.ecr.centralsystem.testobjectfactories;

import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.rental.model.dto.CarRentalRequest;
import pl.kreft.thesis.ecr.centralsystem.rental.model.Rental;
import pl.kreft.thesis.ecr.centralsystem.rental.model.dto.ReturnCarRequest;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

public class RentalFactory {

    public static Long testCarDistance = 500l;
    public static Long testCurrentCarKilometer = 250_000l;
    public static double testFuelLevel = 40.5;

    static public Rental getRandomRental(User lender, Car car) {
        return Rental.builder()
                     .car(car)
                     .lender(lender)
                     .target("Podróż służbowa")
                     .carCondition("OK")
                     .plannedRentalStart(LocalDateTime.now())
                     .plannedRentalEnd(LocalDateTime.now())
                     .applicationDate(Instant.now())
                     .creationDate(Instant.now())
                     .removed(false)
                     .build();
    }

    static public CarRentalRequest prepareRequest(UUID carId) {
        return CarRentalRequest.builder()
                               .rentalCarId(carId)
                               .target("Podróż służbowa")
                               .dateOfStartRent(LocalDateTime.now())
                               .dateOfEndRent(LocalDateTime.now())
                               .build();
    }

    static public ReturnCarRequest prepareReturnRequest(UUID rentalId) {
        return ReturnCarRequest.builder()
                               .rentalId(rentalId)
                               .carCondition("OK")
                               .currentFuelLevel(testFuelLevel)
                               .receivedDescription("OK")
                               .numberKilometerFromMeter(testCurrentCarKilometer)
                               .distanceTraveled(testCarDistance)
                               .build();
    }
}