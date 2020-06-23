package pl.kreft.thesis.ecr.centralsystem.rental.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.lang.Nullable;
import pl.kreft.thesis.ecr.centralsystem.car.model.CarType;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsersRentalHistoryResponse {

    @NonNull int ordinalNumber;

    @NonNull UUID id;

    @NonNull UUID lenderId;

    @NonNull String lenderName;

    @NonNull String lenderSurname;

    @NonNull UUID carId;

    @NonNull CarType carType;

    @NonNull String carBrand;

    @NonNull String carModel;

    @NonNull LocalDateTime applicationDate;

    @NonNull String target;

    @NonNull LocalDateTime plannedRentalStart;

    @NonNull LocalDateTime plannedRentalEnd;

    @Nullable
    Long distanceTraveled;

    @Nullable
    Long numberKilometerFromMeter;

    @Nullable
    String carCondition;

    @Nullable
    Boolean isAcceptedByBoss;

    @Nullable
    Boolean isReturned;

    @Nullable
    LocalDateTime returnDate;

    @Nullable
    Boolean isReceivedPositively;
}