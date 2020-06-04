package pl.kreft.thesis.ecr.centralsystem.rental.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@AllArgsConstructor
public class CarRentalRequest {

    @NonNull
    UUID rentalCarId;

    @NonNull
    UUID lenderUserId;

    @NonNull
    LocalDateTime dateOfStartRent;

    @NonNull
    LocalDateTime dateOfEndRent;

    @NonNull
    String target;
}
