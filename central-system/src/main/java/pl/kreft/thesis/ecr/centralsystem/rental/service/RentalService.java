package pl.kreft.thesis.ecr.centralsystem.rental.service;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.car.repository.CarRepository;
import pl.kreft.thesis.ecr.centralsystem.rental.model.CarRentalRequest;
import pl.kreft.thesis.ecr.centralsystem.rental.model.Rental;
import pl.kreft.thesis.ecr.centralsystem.rental.model.ReturnCarRequest;
import pl.kreft.thesis.ecr.centralsystem.rental.repository.RentalRepository;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;
import pl.kreft.thesis.ecr.centralsystem.user.repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
class RentalService {

    private RentalRepository rentalRepository;
    private UserRepository userRepository;
    private CarRepository carRepository;

    @Autowired
    public RentalService(RentalRepository rentalRepository, UserRepository userRepository, CarRepository carRepository) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.carRepository = carRepository;
    }

    public Rental find(UUID id) throws ObjectNotFoundException {
        Optional<Rental> rental = rentalRepository.findByIdAndRemovedFalse(id);
        if (rental.isPresent()) {
            log.info("Found rental by id: " + id);
            return rental.get();
        }
        throw new ObjectNotFoundException("Unable to locate rental with id: " + id);
    }

    public List<Rental> getAll() {
        log.info("Returning all rental list");
        List<Rental> allRentals = rentalRepository.findAll();
        allRentals =  allRentals.stream().filter(item-> !item.getRemoved()).collect(Collectors.toList());
        return allRentals;
    }

    public List<Rental> getAllByUserId(UUID userId) {
        log.info("Returning all rental list for user Id: " + userId);
        List<Rental> allRentals = rentalRepository.findAllByLenderId(userId);
        allRentals =  allRentals.stream().filter(item-> !item.getRemoved()).collect(Collectors.toList());
        return allRentals;
    }

    public Rental rentCar(CarRentalRequest request) {
        Optional<User> user = userRepository.findById(request.getLenderUserId());
        Optional<Car> car = carRepository.findById(request.getRentalCarId());
        if (user.isPresent() && car.isPresent()) {
            checkIsUserAbilityToRentCar(user.get());
            checkIsCarAbilityForRent(car.get());
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
                    null,
                    null,
                    null,
                    Instant.now(),
                    false
            ));
            log.info("Rental with id: {} successfully saved" ,savedRental.getId() );
            return savedRental;
        }
        log.warn("Could not find input car with id: {} or user with id: {}",
                request.getRentalCarId(), request.getLenderUserId());
        throw new IllegalArgumentException("Argument not exists");
    }

    public void returnCar(ReturnCarRequest returnCarRequest){

    }

    private void checkIsCarAbilityForRent(Car car) {
        //TODO
    }
    private void checkIsUserAbilityToRentCar(User user) {
        //TODO
    }
}