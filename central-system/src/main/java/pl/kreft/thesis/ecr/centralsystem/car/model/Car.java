package pl.kreft.thesis.ecr.centralsystem.car.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "car")
public class Car {

    @Id
    @Type(type = "uuid-char")
    @Column(name = "car_id")
    UUID id;

    @Column(name = "car_status")
    CarStatus status;

    @Column(name = "model")
    String model;

    @Column(name = "brand")
    String brand;

    @Column(name = "type")
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
}
