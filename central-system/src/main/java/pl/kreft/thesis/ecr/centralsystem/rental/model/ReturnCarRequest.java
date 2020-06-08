package pl.kreft.thesis.ecr.centralsystem.rental.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnCarRequest {

    @NonNull
    UUID rentalId;

    @NonNull
    String receivedDescription;

    @NonNull
    Long distanceTraveled;

    @NonNull
    Long numberKilometerFromMeter;

    @NonNull
    String carCondition;

    @NonNull
    double currentFuelLevel;
}
