package pl.kreft.thesis.ecr.centralsystem.rental.service;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.car.service.CarService;
import pl.kreft.thesis.ecr.centralsystem.exception.EcrExceptionMessages;
import pl.kreft.thesis.ecr.centralsystem.exception.ErrorMessage;
import pl.kreft.thesis.ecr.centralsystem.rental.model.CarRentalRequest;
import pl.kreft.thesis.ecr.centralsystem.rental.model.Rental;
import pl.kreft.thesis.ecr.centralsystem.rental.model.RentalHistoryDTO;
import pl.kreft.thesis.ecr.centralsystem.rental.model.ReturnCarRequest;
import pl.kreft.thesis.ecr.centralsystem.rental.repository.RentalRepository;
import pl.kreft.thesis.ecr.centralsystem.rental.service.exception.RentalByEmployeeException;
import pl.kreft.thesis.ecr.centralsystem.rental.service.exception.RentalCarException;
import pl.kreft.thesis.ecr.centralsystem.rental.service.exception.RentalNotFoundException;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserRole;
import pl.kreft.thesis.ecr.centralsystem.user.repository.UserRepository;
import pl.kreft.thesis.ecr.centralsystem.user.service.exception.UserNotExistsException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.kreft.thesis.ecr.centralsystem.config.GlobalConfiguration.defaultTimeZone;
import static pl.kreft.thesis.ecr.centralsystem.exception.EcrExceptionMessages.EmployeeRentalException;

@Slf4j
@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final CarService carService;

    @Autowired
    public RentalService(RentalRepository rentalRepository, UserRepository userRepository,
            CarService carService) {
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
        throw new RentalNotFoundException(
                new ErrorMessage(EcrExceptionMessages.RentalNotFoundException));
    }

    public List<Rental> getAll() {
        log.info("Returning all rental list");
        List<Rental> allRentals = rentalRepository.findAll();
        allRentals = allRentals.stream()
                               .filter(item -> !item.getRemoved())
                               .collect(Collectors.toList());
        return allRentals;
    }

    public List<Rental> getAllByUserId(UUID userId) {
        log.info("Returning all rental list for user Id: " + userId);
        List<Rental> allRentals = rentalRepository.findAllByLenderId(userId);
        allRentals = allRentals.stream()
                               .filter(item -> !item.getRemoved())
                               .collect(Collectors.toList());
        return allRentals;
    }

    public List<Rental> getAllActiveByUserId(UUID userId) {
        log.info("Method getAllActiveByUserId called for user Id: " + userId);
        List<Rental> allRentals = rentalRepository.findAllByLenderId(userId);
        allRentals = allRentals.stream()
                               .filter(item -> !item.getRemoved() && !item.getIsReturned())
                               .collect(Collectors.toList());
        return allRentals;
    }

    @Transactional
    public Rental rentCar(CarRentalRequest request, UUID userId) throws ObjectNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        Car car = carService.find(request.getRentalCarId());
        if (user.isPresent()) {
            checkIsUserAbilityToRentCar(user.get());
            checkIsCarAbilityForRent(car);
            Rental savedRental = rentalRepository.save(Rental.builder()
                                                             .applicationDate(Instant.now())
                                                             .car(car)
                                                             .creationDate(Instant.now())
                                                             .plannedRentalEnd(
                                                                     request.getDateOfEndRent())
                                                             .plannedRentalStart(
                                                                     request.getDateOfStartRent())
                                                             .lender(user.get())
                                                             .removed(false)
                                                             .isReturned(false)
                                                             .target(request.getTarget())
                                                             .build());

            carService.rentCar(car);
            log.info("Rental with id: {} successfully saved", savedRental.getId());
            return savedRental;
        }
        throw new UserNotExistsException(
                new ErrorMessage(EcrExceptionMessages.UserNotExistsException));
    }

    @Transactional
    public Rental returnCar(ReturnCarRequest returnCarRequest) throws ObjectNotFoundException {
        log.info("Method returnCar was called");
        Rental rental = find(returnCarRequest.getRentalId());
        rental.setCarCondition(returnCarRequest.getCarCondition());
        rental.setDistanceTraveled(returnCarRequest.getDistanceTraveled());
        rental.setNumberKilometerFromMeter(returnCarRequest.getNumberKilometerFromMeter());
        rental.setReceivedDescription(returnCarRequest.getReceivedDescription());
        rental.setIsReturned(true);
        rental.setReturnDate(Instant.now());

        carService.returnCar(rental.getCar());
        log.debug("Rental with the following request:" + returnCarRequest.toString()
                + " was returned");
        return rentalRepository.save(rental);
    }

    private void checkIsCarAbilityForRent(Car car) {
        List<Rental> foundRentals = rentalRepository.findAllByCarIdAndPlannedRentalEndIsGreaterThan(
                car.getId(), LocalDateTime.now());

        if (!foundRentals.isEmpty()) {
            throw new RentalCarException(new ErrorMessage(EcrExceptionMessages.RentalCarException));
        }
    }

    private void checkIsUserAbilityToRentCar(User user) {
        if (user.getRole().equals(UserRole.EMPLOYEE)) {
            List<Rental> foundRentals = rentalRepository.findAllByLenderIdAndPlannedRentalEndIsGreaterThan(
                    user.getId(), LocalDateTime.now());
            if (!foundRentals.isEmpty()) {
                throw new RentalByEmployeeException(new ErrorMessage(EmployeeRentalException));
            }
        }
    }

    public List<RentalHistoryDTO> getRentalHistory(UUID lenderId) throws ObjectNotFoundException {
        List<RentalHistoryDTO> results = new ArrayList<>();
        int index = 1;
        List<Rental> rentals = rentalRepository.findAllByLenderId(lenderId);
        for (Rental rental : rentals) {
            Car car = carService.find(rental.getCar().getId());
            results.add(RentalHistoryDTO.builder()
                                        .ordinalNumber(index)
                                        .id(rental.getId())
                                        .applicationDate(
                                                LocalDateTime.ofInstant(rental.getApplicationDate(),
                                                        ZoneId.of(defaultTimeZone)))
                                        .carId(rental.getCar().getId())
                                        .carBrand(car.getBrand())
                                        .carModel(car.getModel())
                                        .carType(car.getType())
                                        .carCondition(rental.getCarCondition())
                                        .distanceTraveled(rental.getDistanceTraveled())
                                        .isAcceptedByBoss(rental.getIsAcceptedByBoss())
                                        .isReceivedPositively(rental.getIsReceivedPositively())
                                        .isReturned(rental.getIsReturned())
                                        .numberKilometerFromMeter(
                                                rental.getNumberKilometerFromMeter())
                                        .plannedRentalEnd(rental.getPlannedRentalEnd())
                                        .plannedRentalStart(rental.getPlannedRentalStart())
                                        .returnDate(LocalDateTime.ofInstant(rental.getReturnDate(),
                                                ZoneId.of(defaultTimeZone)))
                                        .target(rental.getTarget())
                                        .build());
            index++;
        }
        return results;
    }
}