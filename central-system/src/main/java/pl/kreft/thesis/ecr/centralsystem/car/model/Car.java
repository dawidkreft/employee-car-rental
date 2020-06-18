package pl.kreft.thesis.ecr.centralsystem.car.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Type;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    @Column(name = "car_id")
    UUID id;

    @NonNull
    @Column(name = "car_plate", unique = true)
    String plate;

    @NonNull
    @Column(name = "car_status")
    @Enumerated(EnumType.STRING)
    CarStatus status;

    @NonNull
    @Column(name = "car_model")
    String model;

    @NonNull
    @Column(name = "car_brand")
    String brand;

    @NonNull
    @Column(name = "car_type")
    @Enumerated(EnumType.STRING)
    CarType type;

    @Nullable
    @Column(name = "car_date_of_last_review")
    LocalDate dateOfLastReview;

    @Nullable
    @Column(name = "car_date_of_next_review")
    LocalDate dateOfNextReview;

    @Nullable
    @Column(name = "car_date_of_next_technical_examination")
    LocalDate dateOfNextTechnicalExamination;

    @NonNull
    @Column(name = "car_production_year")
    int productionYear;

    @NonNull
    @Column(name = "car_creation_date")
    Instant creationDate;

    @NonNull
    @Column(name = "car_removed")
    Boolean removed;
}