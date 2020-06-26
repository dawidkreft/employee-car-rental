package pl.kreft.thesis.ecr.centralsystem.car.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kreft.thesis.ecr.centralsystem.car.service.CarService;

import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.CAR_REQUEST;

@RequestMapping(CAR_REQUEST)
@Controller
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping
    public String returnAllCars(Model model) {
        model.addAttribute("cars", carService.getAll());
        return "cars";
    }
}
