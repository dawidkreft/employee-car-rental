package pl.kreft.thesis.ecr.centralsystem.rental.view;

import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kreft.thesis.ecr.centralsystem.car.service.CarService;
import pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo;
import pl.kreft.thesis.ecr.centralsystem.common.ResponsePageMappingInfo;
import pl.kreft.thesis.ecr.centralsystem.rental.model.dto.CarRentalRequest;
import pl.kreft.thesis.ecr.centralsystem.rental.model.dto.ReturnCarRequest;
import pl.kreft.thesis.ecr.centralsystem.rental.service.RentalService;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserDetailsProvider;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

import static pl.kreft.thesis.ecr.centralsystem.common.PageRedirectComponent.redirectTo;

@Controller
@RequestMapping(RequestPageMappingInfo.RENTAL_REQUEST)
public class RentalView {

    private final RentalService rentalService;
    private final CarService carService;

    @Autowired
    public RentalView(RentalService rentalService, CarService carService) {
        this.rentalService = rentalService;
        this.carService = carService;
    }

    @RequestMapping
    public String rental(Model model, @AuthenticationPrincipal UserDetailsProvider user) {
        model.addAttribute("activeRentals", rentalService.getAllActiveByUserId(user.getId()));
        return ResponsePageMappingInfo.RENTAL;
    }

    @RequestMapping(RequestPageMappingInfo.CREATE_NEW_REQUEST)
    public String getRentalForm(Model model, @AuthenticationPrincipal UserDetailsProvider user) {
        model.addAttribute("carRentalRequest", new CarRentalRequest());
        model.addAttribute("userID", user.getId().toString());
        model.addAttribute("cars", carService.getAllFreeCars());
        return ResponsePageMappingInfo.RENTAL_FORM;
    }

    @RequestMapping(RequestPageMappingInfo.RENTAL_HISTORY_REQUEST)
    public String getRentalHistory(Model model, @AuthenticationPrincipal UserDetailsProvider user) {
        model.addAttribute("userID", user.getId().toString());
        model.addAttribute("rentals", rentalService.getRentalHistory(user.getId()));
        return ResponsePageMappingInfo.RENTAL_HISTORY;
    }

    @PostMapping
    public String createNewRental(@ModelAttribute CarRentalRequest carRentalRequest,
                                  @AuthenticationPrincipal UserDetailsProvider user) throws ObjectNotFoundException {
        rentalService.rentCar(carRentalRequest, user.getId());
        return redirectTo(RequestPageMappingInfo.RENTAL_REQUEST);
    }

    @GetMapping(RequestPageMappingInfo.RENTAL_RETURN_REQUEST_WITH_ID)
    public String returnRental(Model model, @PathVariable UUID id,
                               @AuthenticationPrincipal UserDetailsProvider user) throws ObjectNotFoundException {
        ReturnCarRequest returnCarRequest = new ReturnCarRequest();
        returnCarRequest.setRentalId(id);
        model.addAttribute("returnCarRequest", returnCarRequest);
        model.addAttribute("car", carService.find(rentalService.find(id).getCar().getId()));
        return ResponsePageMappingInfo.RENTAL_RETURN_FORM;
    }

    @PostMapping(RequestPageMappingInfo.RENTAL_RETURN_REQUEST)
    public String returnCar(@ModelAttribute ReturnCarRequest returnCarRequest,
                            @AuthenticationPrincipal UserDetailsProvider user) {
        rentalService.returnCar(returnCarRequest);
        return redirectTo(RequestPageMappingInfo.RENTAL_REQUEST);
    }

    @GetMapping(RequestPageMappingInfo.RENTAL_USERS_HISTORY)
    public String returnCar(Model model, @AuthenticationPrincipal UserDetailsProvider user) {
        model.addAttribute("rentals", rentalService.getAllUsersRentalBossId(user.getId()));
        return ResponsePageMappingInfo.RENTAL_USERS_HISTORY_PAGE;
    }

    @GetMapping(RequestPageMappingInfo.RENTAL_ACCEPT_ID)
    public String accept(Model model, @PathVariable UUID id,
                         @AuthenticationPrincipal UserDetailsProvider user) {
        rentalService.acceptRental(id, user.getId());
        return redirectTo(RequestPageMappingInfo.RENTAL_REQUEST + RequestPageMappingInfo.RENTAL_USERS_HISTORY);
    }

    @GetMapping("/reports.csv")
    public ResponseEntity<String> getReport(@AuthenticationPrincipal UserDetailsProvider user, HttpServletResponse response) {
        return ResponseEntity
                .ok()
                .contentType(MediaType.valueOf("text/csv"))
                .body(rentalService.getRentalHistoryReportCsvByBossId(user.getId()));
    }
}