package pl.kreft.thesis.ecr.centralsystem.rental.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Type;
import org.springframework.lang.Nullable;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rental")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Type(type = "uuid-char")
    @Column(name = "rental_id")
    UUID id;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lender_user_fk")
    User lender;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_car_fk")
    Car car;

    @NonNull
    @Column(name = "rental_application_date")
    Instant applicationDate;

    @NonNull
    @Column(name = "rental_target")
    String target;

    @NonNull
    @Column(name = "rental_planned_start_date")
    LocalDateTime plannedRentalStart;

    @NonNull
    @Column(name = "rental_planned_end_date")
    LocalDateTime plannedRentalEnd;

    @Nullable
    @Column(name = "rental_distance_traveled")
    Long distanceTraveled;

    @Nullable
    @Column(name = "rental_actual_kilometer_in_car_meter")
    Long numberKilometerFromMeter;

    @Nullable
    @Column(name = "rental_car_condition_description")
    String carCondition;

    @Nullable
    @Column(name = "rental_boss_accepted")
    Boolean isAcceptedByBoss;

    @Nullable
    @Column(name = "rental_is_returned")
    Boolean isReturned;

    @Nullable
    @Column(name = "rental_return_date")
    Instant returnDate;

    @Nullable
    @Column(name = "rental_positively_received")
    Boolean isReceivedPositively;

    @Nullable
    @Column(name = "rental_received_description")
    String receivedDescription;

    @NonNull
    @Column(name = "rental_creation_date")
    Instant creationDate;

    @NonNull
    @Column(name = "rental_removed")
    Boolean removed;
}

