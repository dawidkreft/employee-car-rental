package pl.kreft.thesis.ecr.centralsystem.rental;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kreft.thesis.ecr.centralsystem.rental.model.Rental;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RentalRepository extends JpaRepository<Rental, UUID> {

    Optional<Rental> findById(UUID id);

    Rental save(Rental rental);
}
