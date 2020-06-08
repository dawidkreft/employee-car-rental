package pl.kreft.thesis.ecr.centralsystem.rental.view;

import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kreft.thesis.ecr.centralsystem.car.service.CarService;
import pl.kreft.thesis.ecr.centralsystem.rental.model.CarRentalRequest;
import pl.kreft.thesis.ecr.centralsystem.rental.model.ReturnCarRequest;
import pl.kreft.thesis.ecr.centralsystem.rental.service.RentalService;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserDetailsImpl;

import java.util.UUID;

import static pl.kreft.thesis.ecr.centralsystem.common.PageRedirectComponent.redirectTo;
import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.CREATE_NEW_REQUEST;
import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.RENTAL_HISTORY_REQUEST;
import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.RENTAL_REQUEST;
import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.RENTAL_RETURN_REQUEST;
import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.RENTAL_RETURN_REQUEST_WITH_ID;
import static pl.kreft.thesis.ecr.centralsystem.common.ResponsePageMappingInfo.RENTAL;
import static pl.kreft.thesis.ecr.centralsystem.common.ResponsePageMappingInfo.RENTAL_FORM;
import static pl.kreft.thesis.ecr.centralsystem.common.ResponsePageMappingInfo.RENTAL_HISTORY;
import static pl.kreft.thesis.ecr.centralsystem.common.ResponsePageMappingInfo.RENTAL_RETURN_FORM;

@Controller
@RequestMapping(RENTAL_REQUEST)
public class RentalView {

    private final RentalService rentalService;
    private final CarService carService;

    @Autowired
    public RentalView(RentalService rentalService, CarService carService) {
        this.rentalService = rentalService;
        this.carService = carService;
    }

    @RequestMapping
    public String rental(Model model, @AuthenticationPrincipal UserDetailsImpl user) {
        model.addAttribute("activeRentals", rentalService.getAllActiveByUserId(user.getId()));
        return RENTAL;
    }

    @RequestMapping(CREATE_NEW_REQUEST)
    public String getRentalForm(Model model, @AuthenticationPrincipal UserDetailsImpl user) {
        model.addAttribute("carRentalRequest", new CarRentalRequest());
        model.addAttribute("userID", user.getId().toString());
        model.addAttribute("cars", carService.getAllFreeCars());
        return RENTAL_FORM;
    }

    @RequestMapping(RENTAL_HISTORY_REQUEST)
    public String getRentalHistory(Model model, @AuthenticationPrincipal UserDetailsImpl user)
            throws ObjectNotFoundException {
        model.addAttribute("userID", user.getId().toString());
        model.addAttribute("rentals", rentalService.getRentalHistory(user.getId()));
        return RENTAL_HISTORY;
    }

    @PostMapping
    public String createNewRental(@ModelAttribute CarRentalRequest carRentalRequest,
            @AuthenticationPrincipal UserDetailsImpl user) throws ObjectNotFoundException {
        rentalService.rentCar(carRentalRequest, user.getId());
        return redirectTo(RENTAL_REQUEST);
    }

    @GetMapping(RENTAL_RETURN_REQUEST_WITH_ID)
    public String returnRental(Model model, @PathVariable UUID id,
            @AuthenticationPrincipal UserDetailsImpl user) {
        ReturnCarRequest returnCarRequest = new ReturnCarRequest();
        returnCarRequest.setRentalId(id);
        model.addAttribute("returnCarRequest", returnCarRequest);
        return RENTAL_RETURN_FORM;
    }

    @PostMapping(RENTAL_RETURN_REQUEST)
    public String returnCar(@ModelAttribute ReturnCarRequest returnCarRequest,
            @AuthenticationPrincipal UserDetailsImpl user) throws ObjectNotFoundException {
        rentalService.returnCar(returnCarRequest);
        return redirectTo(RENTAL_REQUEST);
    }

}