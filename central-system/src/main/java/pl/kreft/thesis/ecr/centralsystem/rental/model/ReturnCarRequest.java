package pl.kreft.thesis.ecr.centralsystem.rental.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ReturnCarRequest {

    private UUID rentalId;

    Instant realRentalStartDate;

    Instant realRentalEndDate;

    String receivedDescription;

    Long distanceTraveled;

    Long numberKilometerFromMeter;

    String carCondition;

    double currentFuelLevel;
}
