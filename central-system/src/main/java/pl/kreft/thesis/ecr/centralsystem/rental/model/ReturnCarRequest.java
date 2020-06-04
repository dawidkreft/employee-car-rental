package pl.kreft.thesis.ecr.centralsystem.rental.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class ReturnCarRequest {

    UUID rentalId;

    String receivedDescription;

    Long distanceTraveled;

    Long numberKilometerFromMeter;

    String carCondition;

    double currentFuelLevel;
}
