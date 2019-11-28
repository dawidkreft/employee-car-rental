package pl.kreft.thesis.ecr.centralsystem.inspection;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@Entity
@Table(name = "inspection")
public class Inspection {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Type(type = "uuid-char")
    @Column(name = "inspection_id")
    UUID id;
/*
    @Column(name = "lender_user")
    User lender;

    @Column(name = "rented_car")
    Car rentedCar;

    @Column(name = "inspection_date")
    Instant inspectionDate;

    @Column(name = "description")
    String  description;

    @Column(name = "car_condition")
    VisualCondition carCondition;

    @Column(name = "tire_condition")
    VisualCondition tireCondition;

    @Column(name = "creation_date")
    Instant creationDate;

    @Column(name = "inspection_removed")
    Boolean removed;*/

}