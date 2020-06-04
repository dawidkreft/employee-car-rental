package pl.kreft.thesis.ecr.centralsystem.rental.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kreft.thesis.ecr.centralsystem.rental.model.Rental;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RentalRepository extends JpaRepository<Rental, UUID> {

    Optional<Rental> findById(UUID id);

    Rental save(Rental rental);

    Optional<Rental> findByIdAndRemovedFalse(UUID id);

    List<Rental> findAllByLenderId(UUID userId);

    List<Rental> findAllByCarIdAndPlannedRentalEndIsGreaterThan(UUID carId, LocalDateTime date);

    List<Rental> findAllByLenderIdAndPlannedRentalEndIsGreaterThan(UUID userId, LocalDateTime date);
}
