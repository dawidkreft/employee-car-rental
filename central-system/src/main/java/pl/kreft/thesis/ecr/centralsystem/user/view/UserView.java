package pl.kreft.thesis.ecr.centralsystem.user.view;

import javassist.tools.rmi.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserDetailsProvider;
import pl.kreft.thesis.ecr.centralsystem.user.service.UserService;

import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.USER_REQUESTS;
import static pl.kreft.thesis.ecr.centralsystem.common.ResponsePageMappingInfo.USER;

@Controller
@RequestMapping(USER_REQUESTS)
public class UserView {

    private final UserService userService;

    @Autowired
    public UserView(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUser(Model model, @AuthenticationPrincipal UserDetailsProvider user)
            throws ObjectNotFoundException {
        model.addAttribute("user", userService.getUser(user.getId()));
        return USER;
    }
}
