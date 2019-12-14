package pl.kreft.thesis.ecr.centralsystem.car.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.testobjectfactories.CarFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.kreft.thesis.ecr.centralsystem.dbtestcleaner.DbCleaner.clearDatabase;

@RunWith(SpringRunner.class)
@SpringBootTest
class CarRepositoryTest {

    @Autowired
    CarRepository carRepository;

    @AfterEach
    public void tearDown() {
        try {
            clearDatabase();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Initialize db exception");
        }
    }

    @Test
    public void shouldSaveAndFindById() throws SQLException {
        Car testCar = CarFactory.getCar();
        Car testPremiumCar = CarFactory.getPremiumCar();

        carRepository.save(testCar);
        carRepository.save(testPremiumCar);
        Optional<Car> car = carRepository.findById(testCar.getId());
        List<Car> allCars = carRepository.findAll();

        assertEquals(testCar.getId(), car.get().getId());
        assertEquals(2, allCars.size());
    }

    @Test
    public void shouldSaveAndFindAllCars() {
        carRepository.save(CarFactory.getCar());
        carRepository.save(CarFactory.getCar());
        carRepository.save(CarFactory.getPremiumCar());
        carRepository.save(CarFactory.getPremiumCar());

        List<Car> allCars = carRepository.findAll();

        assertEquals(4, allCars.size());
    }
}