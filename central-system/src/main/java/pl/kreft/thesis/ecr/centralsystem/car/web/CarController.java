package pl.kreft.thesis.ecr.centralsystem.car.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kreft.thesis.ecr.centralsystem.car.service.CarService;

@RequestMapping("/car")
@Controller
public class CarController {

    private CarService carService;

    @Autowired
    public CarController(CarService carService) {
        this.carService = carService;
    }

    @RequestMapping("/all")
    public String returnAllCars(Model model) {
        model.addAttribute("cars", carService.getAll());
        return "cars";
    }

}
