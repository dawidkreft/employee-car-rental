package pl.kreft.thesis.ecr.centralsystem.rental.service;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.kreft.thesis.ecr.centralsystem.calendar.model.DayType;
import pl.kreft.thesis.ecr.centralsystem.calendar.service.HolidayCalendarService;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.car.service.CarService;
import pl.kreft.thesis.ecr.centralsystem.exception.EcrExceptionMessages;
import pl.kreft.thesis.ecr.centralsystem.exception.ErrorMessage;
import pl.kreft.thesis.ecr.centralsystem.rental.model.Rental;
import pl.kreft.thesis.ecr.centralsystem.rental.model.dto.CarRentalRequest;
import pl.kreft.thesis.ecr.centralsystem.rental.model.dto.RentalHistoryResponse;
import pl.kreft.thesis.ecr.centralsystem.rental.model.dto.ReturnCarRequest;
import pl.kreft.thesis.ecr.centralsystem.rental.model.dto.UsersRentalHistoryResponse;
import pl.kreft.thesis.ecr.centralsystem.rental.repository.RentalRepository;
import pl.kreft.thesis.ecr.centralsystem.rental.service.exception.IncorrectCurrentFuelException;
import pl.kreft.thesis.ecr.centralsystem.rental.service.exception.IncorrectDistanceTraveledException;
import pl.kreft.thesis.ecr.centralsystem.rental.service.exception.IncorrectNumberKilometerFromMeterException;
import pl.kreft.thesis.ecr.centralsystem.rental.service.exception.RentalByEmployeeException;
import pl.kreft.thesis.ecr.centralsystem.rental.service.exception.RentalCarException;
import pl.kreft.thesis.ecr.centralsystem.rental.service.exception.RentalDateEndIsBeforeStartException;
import pl.kreft.thesis.ecr.centralsystem.rental.service.exception.RentalNotFoundException;
import pl.kreft.thesis.ecr.centralsystem.rental.service.exception.RentalOnlyWorkDayPossibleException;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserRole;
import pl.kreft.thesis.ecr.centralsystem.user.service.UserService;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static pl.kreft.thesis.ecr.centralsystem.common.DateConverter.convertInstantToLocalDateTime;
import static pl.kreft.thesis.ecr.centralsystem.config.GlobalConfiguration.defaultTimeZone;
import static pl.kreft.thesis.ecr.centralsystem.exception.EcrExceptionMessages.employeeRentalException;

@Slf4j
@Service
public class RentalService {

    private final RentalRepository rentalRepository;
    private final UserService userService;
    private final CarService carService;
    private final HolidayCalendarService holidayCalendarService;

    @Autowired
    public RentalService(RentalRepository rentalRepository, UserService userService,
                         CarService carService, HolidayCalendarService holidayCalendarService) {
        this.rentalRepository = rentalRepository;
        this.userService = userService;
        this.carService = carService;
        this.holidayCalendarService = holidayCalendarService;
    }

    public Rental find(UUID id) {
        Optional<Rental> rental = rentalRepository.findByIdAndRemovedFalse(id);
        if (rental.isPresent()) {
            log.info("Found rental by id: " + id);
            return rental.get();
        }
        throw new RentalNotFoundException(
                new ErrorMessage(EcrExceptionMessages.rentalNotFoundException));
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

    public List<UsersRentalHistoryResponse> getAllUsersRentalBossId(UUID bossId) {
        log.info("Method getAllUsersRentalBossId called");
        List<Rental> rentals = rentalRepository.findAllByLenderBossId(bossId);
        List<UsersRentalHistoryResponse> results = new ArrayList<>();
        int index = 1;
        for (Rental rental : rentals) {
            results.add(mapToUserRentalHistoryResponse(rental, index));
            index++;
        }
        return results;
    }

    public String getRentalHistoryReportCsvByBossId(UUID bossId) {
        String NEW_LINE_SEPARATOR = "\n";
        String COLUMN_SEPARATOR = ";";
        String header = "Lp;Imie;Nazwisko;Marka pojazdu;Model;Typ;Stan pojazdu;Przebyty dystans;Start podróży;Koniec podróży;Data aplikacji";
        StringBuilder results = new StringBuilder().append(header).append(NEW_LINE_SEPARATOR);
        getAllUsersRentalBossId(bossId).forEach(item -> {
            results.append(item.getOrdinalNumber())
                    .append(COLUMN_SEPARATOR)
                    .append(item.getLenderName())
                    .append(COLUMN_SEPARATOR)
                    .append(item.getLenderSurname())
                    .append(COLUMN_SEPARATOR)
                    .append(item.getCarBrand())
                    .append(COLUMN_SEPARATOR)
                    .append(item.getCarModel())
                    .append(COLUMN_SEPARATOR)
                    .append(item.getCarType().getDisplayValue())
                    .append(COLUMN_SEPARATOR)
                    .append(item.getCarCondition())
                    .append(COLUMN_SEPARATOR)
                    .append(item.getDistanceTraveled())
                    .append(COLUMN_SEPARATOR)
                    .append(item.getPlannedRentalStart())
                    .append(COLUMN_SEPARATOR)
                    .append(item.getPlannedRentalEnd())
                    .append(COLUMN_SEPARATOR)
                    .append(item.getApplicationDate())
                    .append(NEW_LINE_SEPARATOR);
        });
        return results.toString();
    }

    private UsersRentalHistoryResponse mapToUserRentalHistoryResponse(Rental rental, int index) {
        return (UsersRentalHistoryResponse.builder()
                .ordinalNumber(index)
                .lenderId(rental.getLender().getId())
                .lenderName(rental.getLender().getName())
                .lenderSurname(rental.getLender().getSurname())
                .id(rental.getId())
                .applicationDate(convertInstantToLocalDateTime(
                        rental.getApplicationDate(), defaultTimeZone))
                .carId(rental.getCar().getId())
                .carBrand(rental.getCar().getBrand())
                .carModel(rental.getCar().getModel())
                .carType(rental.getCar().getType())
                .carCondition(rental.getCarCondition())
                .distanceTraveled(rental.getDistanceTraveled())
                .isAcceptedByBoss(rental.getIsAcceptedByBoss())
                .isReceivedPositively(rental.getIsReceivedPositively())
                .isReturned(rental.getIsReturned())
                .numberKilometerFromMeter(
                        rental.getNumberKilometerFromMeter())
                .plannedRentalEnd(rental.getPlannedRentalEnd())
                .plannedRentalStart(rental.getPlannedRentalStart())
                .returnDate(convertInstantToLocalDateTime(
                        rental.getReturnDate(), defaultTimeZone))
                .target(rental.getTarget())
                .build());
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
        log.info("Method rentCar called for user Id: " + userId);
        log.debug("Method rentCar called for user Id: " + userId + "with following data: " + request
                .toString());
        User user = userService.find(userId);
        Car car = carService.find(request.getRentalCarId());

        checkIfNotHolidayAndDateIsCorrect(request.getDateOfStartRent(), request.getDateOfEndRent());
        checkIsUserAbilityToRentCar(user, request.getDateOfStartRent());
        checkIsCarAbilityForRent(car, request.getDateOfStartRent());

        Rental savedRental = rentalRepository.save(Rental.builder()
                .applicationDate(Instant.now())
                .car(car)
                .creationDate(Instant.now())
                .plannedRentalEnd(
                        request.getDateOfEndRent())
                .plannedRentalStart(
                        request.getDateOfStartRent())
                .lender(user)
                .removed(false)
                .isReturned(false)
                .target(request.getTarget())
                .build());

        carService.rentCar(car);
        log.info("Rental with id: {} successfully saved", savedRental.getId());
        return savedRental;
    }

    @Transactional
    public Rental returnCar(ReturnCarRequest returnCarRequest) {
        log.info("Method returnCar was called");
        log.debug("Method rentCar called with following data: " + returnCarRequest.toString());
        validateReturnCarRequest(returnCarRequest);
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

    private void validateReturnCarRequest(ReturnCarRequest returnCarRequest) {
        if (returnCarRequest.getNumberKilometerFromMeter() < 0) {
            throw new IncorrectNumberKilometerFromMeterException(
                    new ErrorMessage(EcrExceptionMessages.incorrectNumberKilometer));
        }
        if (returnCarRequest.getDistanceTraveled() < 0) {
            throw new IncorrectDistanceTraveledException(
                    new ErrorMessage(EcrExceptionMessages.incorrectTraveledDistance));
        }
        if (returnCarRequest.getCurrentFuelLevel() < 0
                || returnCarRequest.getCurrentFuelLevel() > 100) {
            throw new IncorrectCurrentFuelException(
                    new ErrorMessage(EcrExceptionMessages.incorrectFuelLevel));
        }
    }

    private void checkIsCarAbilityForRent(Car car, @NonNull LocalDateTime dateOfStartRent) {
        List<Rental> foundRentals = rentalRepository.findAllByCarIdAndPlannedRentalEndIsGreaterThan(
                car.getId(), dateOfStartRent);

        if (!foundRentals.isEmpty()) {
            throw new RentalCarException(new ErrorMessage(EcrExceptionMessages.rentalCarException));
        }
    }

    private void checkIsUserAbilityToRentCar(User user, @NonNull LocalDateTime dateOfStartRent) {
        if (user.getRole().equals(UserRole.EMPLOYEE)) {
            List<Rental> foundRentals = rentalRepository.findAllByLenderIdAndPlannedRentalEndIsGreaterThan(
                    user.getId(), dateOfStartRent);
            if (!foundRentals.isEmpty()) {
                throw new RentalByEmployeeException(new ErrorMessage(employeeRentalException));
            }
        }
    }

    public List<RentalHistoryResponse> getRentalHistory(UUID lenderId) {
        List<RentalHistoryResponse> results = new ArrayList<>();
        int index = 1;
        List<Rental> rentals = rentalRepository.findAllByLenderId(lenderId);
        for (Rental rental : rentals) {
            results.add(mapToRentalHistoryResponse(rental, index));
            index++;
        }
        return results;
    }

    private RentalHistoryResponse mapToRentalHistoryResponse(Rental rental, int index) {
        return RentalHistoryResponse.builder()
                .ordinalNumber(index)
                .id(rental.getId())
                .applicationDate(convertInstantToLocalDateTime(
                        rental.getApplicationDate(), defaultTimeZone))
                .carId(rental.getCar().getId())
                .carBrand(rental.getCar().getBrand())
                .carModel(rental.getCar().getModel())
                .carType(rental.getCar().getType())
                .carCondition(rental.getCarCondition())
                .distanceTraveled(rental.getDistanceTraveled())
                .isAcceptedByBoss(rental.getIsAcceptedByBoss())
                .isReceivedPositively(rental.getIsReceivedPositively())
                .isReturned(rental.getIsReturned())
                .numberKilometerFromMeter(rental.getNumberKilometerFromMeter())
                .plannedRentalEnd(rental.getPlannedRentalEnd())
                .plannedRentalStart(rental.getPlannedRentalStart())
                .returnDate(convertInstantToLocalDateTime(
                        rental.getReturnDate(), defaultTimeZone))
                .target(rental.getTarget())
                .build();
    }

    private void checkIfNotHolidayAndDateIsCorrect(@NonNull LocalDateTime dateOfStartRent,
                                                   LocalDateTime dateOfEndRent) {
        checkIfWorkDayOrThrow(dateOfStartRent);
        checkIfWorkDayOrThrow(dateOfStartRent);
        if (dateOfStartRent.isAfter(dateOfEndRent)) {
            throw new RentalDateEndIsBeforeStartException(
                    new ErrorMessage(EcrExceptionMessages.rentalIncorrectDate));
        }
    }

    private void checkIfWorkDayOrThrow(LocalDateTime date) {
        if (holidayCalendarService.checkDay(date.toLocalDate()).equals(DayType.HOLIDAY)
                || DayType.SATURDAY.equals(holidayCalendarService.checkDay(date.toLocalDate())))
            throw new RentalOnlyWorkDayPossibleException(
                    new ErrorMessage(EcrExceptionMessages.rentalOnlyWorkDay));
    }

    public void acceptRental(UUID rentalId, UUID boosId) {
        Rental rental = find(rentalId);
        if (rental.getLender().getBossId().equals(boosId)) {
            rental.setIsAcceptedByBoss(true);
            rentalRepository.save(rental);
        }
    }
}