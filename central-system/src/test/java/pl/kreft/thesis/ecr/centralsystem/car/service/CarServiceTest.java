package pl.kreft.thesis.ecr.centralsystem.car.service;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.car.repository.CarRepository;
import pl.kreft.thesis.ecr.centralsystem.testobjectfactories.CarFactory;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.kreft.thesis.ecr.centralsystem.dbtestcleaner.DbCleaner.clearDatabase;

@RunWith(SpringRunner.class)
@SpringBootTest
class CarServiceTest {

    @Autowired
    CarService carService;

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
    public void shouldSaveAndFindById() throws ObjectNotFoundException {
        Car testCar = CarFactory.getCar();
        Car testPremiumCar = CarFactory.getPremiumCar();

        carService.save(testCar);
        carService.save(testPremiumCar);
        Car car = carService.find(testCar.getId());

        assertEquals(testCar.getId(), car.getId());
    }

    @SneakyThrows
    @Test
    public void shouldRemoveAndReturnExceptionWhenTryFindRemovedCar() {
        Car savedCar = carService.save(CarFactory.getCar());

        Car foundCar = carService.find(savedCar.getId());
        carService.remove(savedCar.getId());

        assertEquals(savedCar.getId(), foundCar.getId());
        assertThrows(ObjectNotFoundException.class, () -> {
            carService.find(savedCar.getId());
        });
    }

    @Test
    void shouldReturnAllCarsWhereRemovedIsFalse() throws ObjectNotFoundException {
        carService.save(CarFactory.getPremiumCar());
        carService.save(CarFactory.getPremiumCar());
        carService.save(CarFactory.getPremiumCar());
        Car savedCar = carService.save(CarFactory.getPremiumCar());

        carService.remove(savedCar.getId());
        List<Car> allCars = carService.getAll();

        assertEquals(3, allCars.size());
        assertEquals(4, carRepository.findAll().size());
    }
}