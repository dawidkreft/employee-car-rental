package pl.kreft.thesis.ecr.centralsystem.rental.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Column;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ReturnCarRequest {

    private UUID rentalId;

    Instant realRentalEnd;

    String receivedDescription;

}
