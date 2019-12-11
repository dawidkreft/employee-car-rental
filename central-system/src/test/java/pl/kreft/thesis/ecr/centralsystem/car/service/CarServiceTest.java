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
import pl.kreft.thesis.ecr.centralsystem.testobjectfactories.CarFactory;

import static org.junit.jupiter.api.Assertions.*;
import static pl.kreft.thesis.ecr.centralsystem.dbtestcleaner.DbCleaner.clearDatabase;

@RunWith(SpringRunner.class)
@SpringBootTest
class CarServiceTest {
    
    @Autowired CarService carService;

    @AfterEach
    public void tearDown() {
        try {
            clearDatabase();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Initialize db exception");
        }
    }

    @Test
    void shouldSaveAndFindById() throws ObjectNotFoundException {
        Car testCar = CarFactory.getCar();
        Car testPremiumCar = CarFactory.getPremiumCar();

        carService.save(testCar);
        carService.save(testPremiumCar);
        Car car = carService.find(testCar.getId());

        assertEquals(testCar.getId(), car.getId());
    }

    @SneakyThrows
    @Test
    void shouldRemoveAndReturnExceptionWhenTryFindRemovedCar() {
        Car savedCar = carService.save(CarFactory.getCar());

        Car foundCar = carService.find(savedCar.getId());
        carService.remove(savedCar.getId());

        assertEquals(savedCar.getId(), foundCar.getId());
        assertThrows(ObjectNotFoundException.class, () -> {
           carService.find(savedCar.getId());
        });
    }
}