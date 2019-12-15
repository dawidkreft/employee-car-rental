package pl.kreft.thesis.ecr.centralsystem.rental.service;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.car.repository.CarRepository;
import pl.kreft.thesis.ecr.centralsystem.car.service.CarService;
import pl.kreft.thesis.ecr.centralsystem.rental.model.CarRentalRequest;
import pl.kreft.thesis.ecr.centralsystem.rental.model.Rental;
import pl.kreft.thesis.ecr.centralsystem.rental.model.ReturnCarRequest;
import pl.kreft.thesis.ecr.centralsystem.rental.repository.RentalRepository;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserRole;
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
    private CarService carService;

    @Autowired
    public RentalService(RentalRepository rentalRepository, UserRepository userRepository, CarService carService) {
        this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
        this.carService = carService;
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

    public Rental rentCar(CarRentalRequest request) throws ObjectNotFoundException {
        Optional<User> user = userRepository.findById(request.getLenderUserId());
        Car car = carService.find(request.getRentalCarId());
        if (user.isPresent()) {
            checkIsUserAbilityToRentCar(user.get());
            checkIsCarAbilityForRent(car);
            Rental savedRental = rentalRepository.save(new Rental(
                    user.get(),
                    car,
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

    public Rental returnCar(ReturnCarRequest returnCarRequest) throws ObjectNotFoundException {
        Rental rental = find(returnCarRequest.getRentalId());
        rental.setRealRentalStart(returnCarRequest.getRealRentalStartDate());
        rental.setRealRentalEnd(returnCarRequest.getRealRentalEndDate());
        rental.setCarCondition(returnCarRequest.getCarCondition());
        rental.setDistanceTraveled(returnCarRequest.getDistanceTraveled());
        rental.setNumberKilometerFromMeter(returnCarRequest.getNumberKilometerFromMeter());
        rental.setReceivedDescription(returnCarRequest.getReceivedDescription());

        Car car = carService.find(rental.getCar().getId());
        car.setCurrentCourse(returnCarRequest.getNumberKilometerFromMeter());
        car.setCurrentFuelLevel(returnCarRequest.getCurrentFuelLevel());
        carService.save(car);
        log.info("Rental with id: {} was return;",rental.getId());
        return rentalRepository.save(rental);
    }

    private void checkIsCarAbilityForRent(Car car) {
        List<Rental> foundRentals = rentalRepository.findAllByCarIdAndRealRentalEndIsNull(
                car.getId());

        if(!foundRentals.isEmpty()){
            throw new IllegalArgumentException(String.format("Car with id: {} is not available",car.getId()));
        }
    }

    private void checkIsUserAbilityToRentCar(User user) {
       if(user.getRole().equals(UserRole.EMPLOYEE)){
           List<Rental> foundRentals = rentalRepository.findAllByLenderIdAndRealRentalEndIsNull(
                   user.getId());
           if(!foundRentals.isEmpty()){
               throw new IllegalArgumentException(String.format("User with id: {} cannot rent a car",user.getId()));
           }
       }
    }
}