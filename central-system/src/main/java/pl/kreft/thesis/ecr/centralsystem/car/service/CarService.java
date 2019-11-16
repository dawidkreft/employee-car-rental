package pl.kreft.thesis.ecr.centralsystem.car.service;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.car.repository.CarRepository;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class CarService {

    private CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Car find(UUID id) throws Exception {
        Optional<Car> car = carRepository.findById(id);
        if (car.isPresent()) {
           log.info("Found car by id: " + id);
            return car.get();
        }
        throw new ObjectNotFoundException("Unable to locate car with id: " + id);
    }

    public boolean save(Car car) throws Exception {
        Optional<Car> foundCar = carRepository.findById(car.getId());
        if (foundCar.isPresent()) {
            log.info("Found car by id: " + car.getId());
            Car updatedCar = car.

    //TODO
        }else{

        }
        return carRepository.findById(id).isPresent();
    }

}
