package pl.kreft.thesis.ecr.centralsystem.car.service;

import javassist.tools.rmi.ObjectNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.car.model.CarStatus;
import pl.kreft.thesis.ecr.centralsystem.car.model.NewCarRequest;
import pl.kreft.thesis.ecr.centralsystem.car.repository.CarRepository;
import pl.kreft.thesis.ecr.centralsystem.testobjectfactories.NewCarRequestFactory;

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
    public void shouldSaveNewCarAndFindById() throws ObjectNotFoundException {
        NewCarRequest testCar = NewCarRequestFactory.getNewCarRequest();

        Car savedCar = carService.save(testCar);
        Car car = carService.find(savedCar.getId());

        assertEquals(testCar.getBrand(), car.getBrand());
        assertEquals(testCar.getModel(), car.getModel());
        assertEquals(testCar.getDateOfLastReview(), car.getDateOfLastReview());
        assertEquals(testCar.getDateOfNextReview(), car.getDateOfNextReview());
    }

    @Test
    public void shouldRemoveAndReturnExceptionWhenTryFindRemovedCar()
            throws ObjectNotFoundException {
        Car savedCar = carService.save(NewCarRequestFactory.getNewCarRequest());

        Car foundCar = carService.find(savedCar.getId());
        carService.remove(savedCar.getId());

        assertEquals(savedCar.getId(), foundCar.getId());
        assertThrows(ObjectNotFoundException.class, () -> {
            carService.find(savedCar.getId());
        });
    }

    @Test
    void shouldReturnAllCarsWhereRemovedIsFalse() throws ObjectNotFoundException {
        carService.save(NewCarRequestFactory.getNewCarRequestForPremiumCar());
        carService.save(NewCarRequestFactory.getNewCarRequestForPremiumCar());
        carService.save(NewCarRequestFactory.getNewCarRequestForPremiumCar());
        Car savedCar = carService.save(NewCarRequestFactory.getNewCarRequestForPremiumCar());

        carService.remove(savedCar.getId());
        List<Car> allCars = carService.getAll();

        assertEquals(3, allCars.size());
        assertEquals(4, carRepository.findAll().size());
    }

    @Test
    void shouldReturnAllFreeCars() throws ObjectNotFoundException {
        carService.save(NewCarRequestFactory.getNewCarRequestForPremiumCar());
        Car savedCar = carService.save(NewCarRequestFactory.getNewCarRequestForPremiumCar());
        savedCar.setStatus(CarStatus.LOAN);
        carRepository.save(savedCar);

        List<Car> allFreeCars = carService.getAllFreeCars();
        List<Car> allCars = carService.getAll();

        assertEquals(1, allFreeCars.size());
        assertEquals(2, allCars.size());
    }
}