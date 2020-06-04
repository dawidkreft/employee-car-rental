package pl.kreft.thesis.ecr.centralsystem.inspection.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.Type;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "inspection")
public class Inspection {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    String description;

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

}