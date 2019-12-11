package pl.kreft.thesis.ecr.centralsystem.rental.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class CarRentalRequest {
    private UUID rentalCarId;

    private UUID lenderUserId;

    private Instant dateOfStartRent;

    private Instant dateOfEndRent;

    private String target;
}
