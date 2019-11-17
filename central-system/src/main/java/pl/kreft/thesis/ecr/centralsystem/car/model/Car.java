package pl.kreft.thesis.ecr.centralsystem.car.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Type(type = "uuid-char")
    @Column(name = "car_id")
    UUID id;

    @Column(name = "car_status")
    @Enumerated(EnumType.STRING)
    CarStatus status;

    @Column(name = "model")
    String model;

    @Column(name = "brand")
    String brand;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    CarType type;

    @Column(name = "production_date")
    Instant productionDate;

    @Column(name = "current_course")
    Long currentCourse;

    @Column(name = "current_fuel_level")
    double currentFuelLevel;

    @Column(name = "date_of_last_review")
    Instant dateOfLastReview;

    @Column(name = "date_of_next_review")
    Instant dateOfNextReview;

    @Column(name = "date_of_next_technical_examination")
    Instant dateOfNextTechnicalExamination;

    @Column(name = "car_creation_date")
    Instant creationDate;

    @Column(name = "car_removed")
    Boolean removed;

    public Car(CarStatus status, String model, String brand, CarType type, Instant productionDate, Long currentCourse, double currentFuelLevel, Instant dateOfLastReview, Instant dateOfNextReview, Instant dateOfNextTechnicalExamination, Instant creationDate, Boolean removed) {
        this.status = status;
        this.model = model;
        this.brand = brand;
        this.type = type;
        this.productionDate = productionDate;
        this.currentCourse = currentCourse;
        this.currentFuelLevel = currentFuelLevel;
        this.dateOfLastReview = dateOfLastReview;
        this.dateOfNextReview = dateOfNextReview;
        this.dateOfNextTechnicalExamination = dateOfNextTechnicalExamination;
        this.creationDate = creationDate;
        this.removed = removed;
    }
}
