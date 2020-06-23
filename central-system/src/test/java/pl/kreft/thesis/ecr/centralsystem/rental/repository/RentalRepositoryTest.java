package pl.kreft.thesis.ecr.centralsystem.rental.repository;

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
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory.getBoss;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory.getEmployee;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory.getSecondEmployee;

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

    @Test
    void shouldFindAllUserRentalByBoss() {
        User boos = userRepository.save(getBoss());
        lender.setBossId(boos.getId());
        lender1.setBossId(boos.getId());
        userRepository.save(lender);
        userRepository.save(lender1);
        rentalRepository.save(getRandomRental(lender, car));
        rentalRepository.save(getRandomRental(lender1, car));

        List<Rental> rental = rentalRepository.findAllByLenderBossId(boos.getId());

        assertEquals(2, rental.size());
        assertEquals(lender, rental.get(0).getLender());
        assertEquals(lender1, rental.get(1).getLender());
    }

}