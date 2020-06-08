package pl.kreft.thesis.ecr.centralsystem.car.service;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.car.model.CarStatus;
import pl.kreft.thesis.ecr.centralsystem.car.model.NewCarRequest;
import pl.kreft.thesis.ecr.centralsystem.car.repository.CarRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CarService {

    private final CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car find(UUID id) throws ObjectNotFoundException {
        Optional<Car> car = carRepository.findByIdAndRemovedFalse(id);
        if (car.isPresent()) {
            log.info("Found car by id: " + id);
            return car.get();
        }
        throw new ObjectNotFoundException("Unable to locate car with id: " + id);
    }

    public Car save(NewCarRequest car) {
        Car newCar = Car.builder()
                        .brand(car.getBrand())
                        .model(car.getModel())
                        .type(car.getType())
                        .productionYear(car.getProductionYear())
                        .dateOfLastReview(car.getDateOfLastReview())
                        .dateOfNextReview(car.getDateOfNextReview())
                        .dateOfNextTechnicalExamination(car.getDateOfNextTechnicalExamination())
                        .status(CarStatus.AVAILABLE)
                        .creationDate(Instant.now())
                        .removed(false)
                        .build();
        return carRepository.save(newCar);
    }

    public Car save(Car car) {
        return carRepository.save(car);
    }

    public List<Car> getAll() {
        log.info("Returning all cars");
        List<Car> allCars = carRepository.findAll();
        allCars = allCars.stream().filter(item -> !item.getRemoved()).collect(Collectors.toList());
        return allCars;
    }

    public List<Car> getAllFreeCars() {
        log.info("Returning all free cars");
        List<Car> allCars = carRepository.findAll();
        allCars = allCars.stream()
                         .filter(item -> !item.getRemoved() && item.getStatus().equals(CarStatus.AVAILABLE))
                         .collect(Collectors.toList());
        return allCars;
    }

    public void remove(UUID carId) throws ObjectNotFoundException {
        Optional<Car> car = carRepository.findByIdAndRemovedFalse(carId);
        if (car.isPresent()) {
            Car carInDb = car.get();
            carInDb.setRemoved(true);
            carRepository.save(carInDb);
            log.info("Removed car by id: " + carId);
        } else {
            throw new ObjectNotFoundException("Unable to locate car with id: " + carId);
        }
    }

    public void rentCar(Car car) {
        log.info("Changing car status to busy with id:" + car.getId());
        car.setStatus(CarStatus.LOAN);
        carRepository.save(car);
    }

    public void returnCar(Car car) {
        log.info("Changing car status to free with id:" + car.getId());
        car.setStatus(CarStatus.AVAILABLE);
        carRepository.save(car);
    }
}