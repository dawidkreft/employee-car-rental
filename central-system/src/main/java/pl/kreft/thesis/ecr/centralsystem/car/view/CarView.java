package pl.kreft.thesis.ecr.centralsystem.car.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kreft.thesis.ecr.centralsystem.car.service.CarService;

@RequestMapping("/car")
@Controller
public class CarView {

    private CarService carService;

    @Autowired
    public CarView(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/all")
    public String returnAllCars(Model model) {
        model.addAttribute("cars", carService.getAll());
        return "cars";
    }

}
