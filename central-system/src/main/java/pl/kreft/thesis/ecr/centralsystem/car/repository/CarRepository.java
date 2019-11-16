package pl.kreft.thesis.ecr.centralsystem.car.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {

    Optional<Car> findById(UUID id);

    Car save(Car car);
}
