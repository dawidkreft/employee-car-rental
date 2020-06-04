package pl.kreft.thesis.ecr.centralsystem.car.model;

import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Data
public class UpdateCarRequest {

    @NonNull
    CarStatus carStatus;

    @Nullable
    LocalDate dateOfLastReview;

    @Nullable
    LocalDate dateOfNextReview;

    @Nullable
    LocalDate dateOfNextTechnicalExamination;
}
