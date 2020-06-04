package pl.kreft.thesis.ecr.centralsystem.car.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.lang.Nullable;

import java.time.LocalDate;

@Data
@Builder
public class NewCarRequest {

    @NonNull 
    String brand;

    @NonNull 
    String model;

    @NonNull 
    CarType type;

    @Nullable
    LocalDate dateOfLastReview;

    @Nullable
    LocalDate dateOfNextReview;

    @Nullable
    LocalDate dateOfNextTechnicalExamination;

    @NonNull
    int productionYear;
}
