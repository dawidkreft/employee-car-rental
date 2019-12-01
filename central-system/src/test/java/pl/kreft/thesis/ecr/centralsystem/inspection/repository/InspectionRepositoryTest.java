package pl.kreft.thesis.ecr.centralsystem.inspection.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.car.repository.CarRepository;
import pl.kreft.thesis.ecr.centralsystem.inspection.model.Inspection;
import pl.kreft.thesis.ecr.centralsystem.user.model.User;
import pl.kreft.thesis.ecr.centralsystem.user.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.CarFactory.getCar;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.InspectionFactory.getRandomInspection;
import static pl.kreft.thesis.ecr.centralsystem.testobjectfactories.UserFactory.getInspector;

@RunWith(SpringRunner.class)
@SpringBootTest
class InspectionRepositoryTest {

    @Autowired InspectionRepository inspectionRepository;

    @Autowired UserRepository userRepository;

    @Autowired CarRepository carRepository;

    private Car car;
    private User inspector;
    private User inspector1;

    @BeforeEach
    public void setUp() {
        car = getCar();
        inspector = getInspector();
        inspector1 = getInspector();
        carRepository.save(car);
        userRepository.save(inspector);
        userRepository.save(inspector1);
    }

    @AfterEach
    public void clearDB() {
        inspectionRepository.deleteAll();
        userRepository.deleteAll();
        carRepository.deleteAll();
    }

    @Test
    void shouldSaveAndFindById() {
        Inspection inspectionTest = getRandomInspection(inspector, car);
        Inspection inspectionTest1 = getRandomInspection(inspector1, car);

        inspectionRepository.save(inspectionTest);
        inspectionRepository.save(inspectionTest1);
        Optional<Inspection> inspection = inspectionRepository.findById(inspectionTest.getId());
        List<Inspection> allInspections = inspectionRepository.findAll();

        assertEquals(inspectionTest.getId(), inspection.get().getId());
        assertEquals(2, allInspections.size());
    }
}