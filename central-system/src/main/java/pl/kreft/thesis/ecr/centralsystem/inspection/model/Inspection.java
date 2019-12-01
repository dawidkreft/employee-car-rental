package pl.kreft.thesis.ecr.centralsystem.inspection.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspector_fk")
    User inspector;

    @NonNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inspected_car_fk")
    Car inspectedCar;

    @Column(name = "inspection_date")
    Instant inspectionDate;

    @Column(name = "description")
    String  description;

    @Column(name = "car_condition")
    @Enumerated(EnumType.STRING)
    VisualCondition carCondition;

    @Column(name = "tire_condition")
    @Enumerated(EnumType.STRING)
    VisualCondition tireCondition;

    @Column(name = "creation_date")
    Instant creationDate;

    @Column(name = "inspection_removed")
    Boolean removed;

    public Inspection(@NonNull User inspector, @NonNull Car inspectedCar, Instant inspectionDate,
            String description, VisualCondition carCondition, VisualCondition tireCondition,
            Instant creationDate, Boolean removed) {
        this.inspector = inspector;
        this.inspectedCar = inspectedCar;
        this.inspectionDate = inspectionDate;
        this.description = description;
        this.carCondition = carCondition;
        this.tireCondition = tireCondition;
        this.creationDate = creationDate;
        this.removed = removed;
    }
}