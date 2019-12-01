package pl.kreft.thesis.ecr.centralsystem.rental.model;

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
@Table(name = "rental")
public class Rental {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
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

    public Rental(@NonNull User lender, @NonNull Car car, Instant applicationDate, String target,
            Instant plannedRentalStart, Instant plannedRentalEnd, Instant realRentalStart,
            Instant realRentalEnd, Boolean isAcceptedByBoss, Boolean isReceivedPositively,
            String receivedDescription, Boolean isReceivedCheckedByDispatcher,
            String dispatcherComment, Instant creationDate, Boolean removed) {
        this.lender = lender;
        this.car = car;
        this.applicationDate = applicationDate;
        this.target = target;
        this.plannedRentalStart = plannedRentalStart;
        this.plannedRentalEnd = plannedRentalEnd;
        this.realRentalStart = realRentalStart;
        this.realRentalEnd = realRentalEnd;
        this.isAcceptedByBoss = isAcceptedByBoss;
        this.isReceivedPositively = isReceivedPositively;
        this.receivedDescription = receivedDescription;
        this.isReceivedCheckedByDispatcher = isReceivedCheckedByDispatcher;
        this.dispatcherComment = dispatcherComment;
        this.creationDate = creationDate;
        this.removed = removed;
    }
}

