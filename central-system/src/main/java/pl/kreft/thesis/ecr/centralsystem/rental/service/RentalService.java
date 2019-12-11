package pl.kreft.thesis.ecr.centralsystem.rental.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.car.repository.CarRepository;
import pl.kreft.thesis.ecr.centralsystem.rental.model.CarRentalRequest;
import pl.kreft.thesis.ecr.centralsystem.rental.model.Rental;
import pl.kreft.thesis.ecr.centralsystem.rental.repository.RentalRepository;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;
import pl.kreft.thesis.ecr.centralsystem.user.repository.UserRepository;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
class RentalService {

    private RentalRepository rentalRepository;
    private UserRepository userRepository;
    private CarRepository carRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public boolean rentCar(CarRentalRequest request) {
        Optional<User> user = userRepository.findById(request.getLenderUserId());
        Optional<Car> car = carRepository.findById(request.getRentalCarId());
        if (user.isPresent() && car.isPresent()) {
            Rental savedRental = rentalRepository.save(new Rental(
                    user.get(),
                    car.get(),
                    Instant.now(),
                    request.getTarget(),
                    request.getDateOfStartRent(),
                    request.getDateOfEndRent(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    null,
                    Instant.now(),
                    false
            ));
            log.info("Rental with id: {} successfully saved" ,savedRental.getId() );
            return true;
        }
        log.warn("Could not find input car with id: {} or user with id: {}",
                request.getRentalCarId(), request.getLenderUserId());
        throw new IllegalArgumentException("Argument not exists");
    }
}