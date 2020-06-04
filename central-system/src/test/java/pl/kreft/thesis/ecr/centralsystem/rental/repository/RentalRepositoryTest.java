package pl.kreft.thesis.ecr.centralsystem.rental.repository;

import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.car.repository.CarRepository;
import pl.kreft.thesis.ecr.centralsystem.rental.model.Rental;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;
import pl.kreft.thesis.ecr.centralsystem.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static pl.kreft.thesis.ecr.centralsystem.dbtestcleaner.DbCleaner.clearDatabase;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.CarFactory.getCar;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.RentalFactory.getRandomRental;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory.getEmployee;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory.getSecondEmployee;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory.getSecondInspector;

@RunWith(SpringRunner.class)
@SpringBootTest
class RentalRepositoryTest {

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
        lender1 = getSecondEmployee();
        carRepository.save(car);
        userRepository.save(lender);
        userRepository.save(lender1);
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
    void shouldSaveAndFindById() {
        Rental testRental = getRandomRental(lender, car);
        Rental testRental1 = getRandomRental(lender1, car);

        rentalRepository.save(testRental);
        rentalRepository.save(testRental1);
        Optional<Rental> rental = rentalRepository.findById(testRental.getId());
        List<Rental> allRentals = rentalRepository.findAll();

        assertEquals(testRental.getId(), rental.get().getId());
        assertEquals(2, allRentals.size());
    }

  /*  @Test
    void shouldFindRentalFromUserIdWhenNotReturnCar() {
        Rental testRental = getRandomRental(lender, car);
        Rental savedRental = rentalRepository.save(testRental);

        List<Rental> foundRentals = rentalRepository.findAllByLenderIdAndRealRentalEndIsNull(
                lender.getId());

        assertEquals(1, foundRentals.size());
        assertEquals(lender.getId(),foundRentals.get(0).getLender().getId());
        assertEquals(savedRental.getId(),foundRentals.get(0).getId());
    }

    @Test
    void shouldFindRentalFromCarIdWhenIsNotReturn() {
        Rental testRental = getRandomRental(lender, car);
        Rental savedRental = rentalRepository.save(testRental);

        List<Rental> foundRentals = rentalRepository.findAllByCarIdAndRealRentalEndIsNull(
                car.getId());

        assertEquals(1, foundRentals.size());
        assertEquals(car.getId(),foundRentals.get(0).getCar().getId());
        assertEquals(savedRental.getId(),foundRentals.get(0).getId());
    }*/
}