package pl.kreft.thesis.ecr.centralsystem.car.service;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kreft.thesis.ecr.centralsystem.car.model.Car;
import pl.kreft.thesis.ecr.centralsystem.car.repository.CarRepository;
import pl.kreft.thesis.ecr.centralsystem.rental.service.RentalService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class CarService {

    private RentalService rentalService;

    private CarRepository carRepository;

    @Autowired
    public CarService(RentalService rentalService, CarRepository carRepository) {
        this.rentalService = rentalService;
        this.carRepository = carRepository;
    }

    public Car find(UUID id) throws ObjectNotFoundException {
        Optional<Car> car = carRepository.findByIdAndRemovedFalse(id);
        if (car.isPresent()) {
           log.info("Found car by id: " + id);
            return car.get();
        }
        throw new OutOfMemoryError("Unable to locate car with id: " + id);
    }

    public Car save(Car car)  {
        Optional<Car> carInDbOptional = carRepository.findByIdAndRemovedFalse(car.getId());
        Car savedCar;
        if(carInDbOptional.isPresent()){
            Car oldCar = carInDbOptional.get();
            oldCar.setBrand(car.getBrand());
            oldCar.setModel(car.getModel());
            oldCar.setCurrentCourse(car.getCurrentCourse());
            oldCar.setCurrentFuelLevel(car.getCurrentFuelLevel());
            oldCar.setDateOfLastReview(car.getDateOfLastReview());
            oldCar.setDateOfNextReview(car.getDateOfNextReview());
            oldCar.setDateOfNextTechnicalExamination(car.getDateOfNextTechnicalExamination());
            oldCar.setProductionDate(car.getProductionDate());
            oldCar.setStatus(car.getStatus());
            oldCar.setType(car.getType());
            savedCar = carRepository.save(oldCar);
            log.info("Update old car in db to : " + car.toString());
        }else{
           savedCar = carRepository.save(car);
            log.info("Saved new car in db : " + car.toString());
        }
        return savedCar;
    }

    public List<Car> getAll() {
        log.info("Returned all car");
        return carRepository.findAll();
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
}