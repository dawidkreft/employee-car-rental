package pl.kreft.thesis.ecr.centralsystem.rental.model;

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
@Table(name = "rental")
public class Rental {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Type(type = "uuid-char")
    @Column(name = "rental_id")
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lender_user_fk")
    User lender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rental_car_fk")
    Car car;

    @Column(name = "application_date")
    Instant applicationDate;

    @Column(name = "target")
    String target;

    @Column(name = "planned_rental_start_date")
    Instant plannedRentalStart;

    @Column(name = "planned_rental_end_date")
    Instant plannedRentalEnd;

    @Column(name = "real_rental_start_date")
    Instant realRentalStart;

    @Column(name = "real_rental_end_date")
    Instant realRentalEnd;

    @Column(name = "boss_accepted")
    Boolean isAcceptedByBoss;

    @Column(name = "positively_received")
    Boolean isReceivedPositively;

    @Column(name = "received_description")
    String receivedDescription;

    @Column(name = "received_checked_by_dispatcher")
    Boolean isReceivedCheckedByDispatcher;

    @Column(name = "dispatcher_comment")
    String dispatcherComment;

    @Column(name = "creation_date")
    Instant creationDate;

    @Column(name = "rental_removed")
    Boolean removed;
}

