package pl.kreft.thesis.ecr.centralsystem.rental.service;

import javassist.tools.rmi.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.car.repository.CarRepository;
import pl.kreft.thesis.ecr.centralsystem.rental.model.CarRentalRequest;
import pl.kreft.thesis.ecr.centralsystem.rental.model.Rental;
import pl.kreft.thesis.ecr.centralsystem.rental.repository.RentalRepository;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;
import pl.kreft.thesis.ecr.centralsystem.user.repository.UserRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static pl.kreft.thesis.ecr.centralsystem.dbtestcleaner.DbCleaner.clearDatabase;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.CarFactory.getCar;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.RentalFactory.getRandomRental;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.RentalFactory.prepareRequest;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory.getEmployee;

@RunWith(SpringRunner.class)
@SpringBootTest
class RentalServiceTest {

    @Autowired
    RentalService rentalService;

    @Autowired
    RentalRepository rentalRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    CarRepository carRepository;

    private Car car;
    private User lender;
    private User lender1;

    @BeforeEach
    public void setUp() {
        car = getCar();
        lender = getEmployee();
        lender1 = getEmployee();
        car = carRepository.save(car);
        lender = userRepository.save(lender);
        lender1 = userRepository.save(lender1);
    }

    @AfterEach
    public void tearDown() {
        try {
            clearDatabase();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Initialize db exception");
        }
    }

    @Test
    public void shouldFindAllAndFindByUserId() {
        Rental testRental = getRandomRental(lender, car);
        Rental testRental1 = getRandomRental(lender1, car);
        Rental savedRental = rentalRepository.save(testRental);
        rentalRepository.save(testRental1);

        List<Rental> allRentals = rentalService.getAll();
        List<Rental> allByUserId = rentalService.getAllByUserId(lender.getId());

        assertEquals(2, allRentals.size());
        assertEquals(1, allByUserId.size());
        assertEquals(lender.getId(), allByUserId.get(0).getLender().getId());
        assertEquals(savedRental.getId(), allByUserId.get(0).getId());
    }

    @Test
    public void shouldSaveNewRentalRequestAndFind() throws ObjectNotFoundException {
        CarRentalRequest carRentalRequest = prepareRequest(lender.getId(), car.getId());

        Rental rental = rentalService.rentCar(carRentalRequest);
        Rental rentalInDb = rentalService.find(rental.getId());

        assertEquals(rental.getId(), rentalInDb.getId());
        assertEquals(rental.getPlannedRentalStart().getEpochSecond(), rentalInDb.getPlannedRentalStart().getEpochSecond());
        assertEquals(lender.getId(), rentalInDb.getLender().getId());
        assertEquals(car.getId(), rentalInDb.getCar().getId());
    }
}