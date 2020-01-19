package pl.kreft.thesis.ecr.centralsystem.rental.view;

import javassist.tools.rmi.ObjectNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kreft.thesis.ecr.centralsystem.rental.model.CarRentalRequest;
import pl.kreft.thesis.ecr.centralsystem.rental.model.Rental;
import pl.kreft.thesis.ecr.centralsystem.rental.service.RentalService;

import java.util.UUID;

@Slf4j @RequestMapping("/rent") @Controller public class RentalView {

    private RentalService rentalService;
    private final String CAR_RENTAL_MODEL_ATTRIBUTE = "carRentalRequest";

    @Autowired public RentalView(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("/{carId}/{lenderId}") public String showForm(@PathVariable UUID carId,
            @PathVariable UUID lenderId, Model model) {
        model.addAttribute(CAR_RENTAL_MODEL_ATTRIBUTE, new CarRentalRequest());
        model.addAttribute("carId", carId);
        model.addAttribute("lenderId", lenderId);// TODO Change when will you implement user login
        log.debug("Returning processFormView -> rent, with car id: " + carId + "and lenderId: "
                + lenderId);
        return "rent";
    }

    @PostMapping("/{carId}/{lenderId}") public String processForm(@PathVariable UUID carId,
            @PathVariable UUID lenderId, CarRentalRequest carRentalRequest, Model model) {
        carRentalRequest.setLenderUserId(lenderId);
        carRentalRequest.setRentalCarId(carId);
        Rental rental;
        try {
            rental = rentalService.rentCar(carRentalRequest);
        } catch (ObjectNotFoundException e) {
            model.addAttribute("contents", e.toString());
            return "rentalException";
        }
        model.addAttribute("rental", rental);
        return "rentalSuccess";
    }
}
