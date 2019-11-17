package pl.kreft.thesis.ecr.centralsystem.car.repository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.testobjectfactories.CarFactory;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class CarRepositoryTest {

    @Autowired
    CarRepository carRepository;

    @Test
    void shouldSaveAndFindById() {
        Car testCar = CarFactory.getCar();
        Car testPremiumCar = CarFactory.getPremiumCar();

        carRepository.save(testCar);
        carRepository.save(testPremiumCar);
        Optional<Car> car = carRepository.findById(testCar.getId());
        List<Car> allCars = carRepository.findAll();

        assertEquals(testCar.getId(), car.get().getId());
        assertEquals(2, allCars.size());
    }
}