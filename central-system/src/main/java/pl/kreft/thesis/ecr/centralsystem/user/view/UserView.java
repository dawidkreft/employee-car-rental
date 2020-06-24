package pl.kreft.thesis.ecr.centralsystem.user.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserDetailsProvider;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserRequest;
import pl.kreft.thesis.ecr.centralsystem.user.service.UserService;

import java.util.UUID;

import static pl.kreft.thesis.ecr.centralsystem.common.PageRedirectComponent.redirectTo;
import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.USER_BY_BOSS;
import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.USER_FORM;
import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.USER_REMOVE_ID;
import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.USER_REQUESTS;
import static pl.kreft.thesis.ecr.centralsystem.common.ResponsePageMappingInfo.USER;
import static pl.kreft.thesis.ecr.centralsystem.common.ResponsePageMappingInfo.USERS;
import static pl.kreft.thesis.ecr.centralsystem.common.ResponsePageMappingInfo.USER_FORM_PAGE;

@Controller
@RequestMapping(USER_REQUESTS)
public class UserView {

    private final UserService userService;

    @Autowired
    public UserView(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getUser(Model model, @AuthenticationPrincipal UserDetailsProvider user) {
        model.addAttribute("user", userService.getUser(user.getId()));
        return USER;
    }

    @GetMapping(USER_BY_BOSS)
    public String getUsersByBoss(Model model, @AuthenticationPrincipal UserDetailsProvider user) {
        model.addAttribute("users", userService.getAllUsersByBossId(user.getId()));
        return USERS;
    }

    @GetMapping(USER_REMOVE_ID)
    public String remove(@PathVariable UUID id, @AuthenticationPrincipal UserDetailsProvider user) {
        userService.remove(id);
        return redirectTo(USER_REQUESTS + USER_BY_BOSS);
    }

    @GetMapping(USER_FORM)
    public String addUserForm(Model model) {
        model.addAttribute("newUser", new UserRequest());
        return USER_FORM_PAGE;
    }

    @PostMapping
    public String addUser(@ModelAttribute UserRequest userRequest, @AuthenticationPrincipal UserDetailsProvider user) {
        userService.addNew(userRequest, user);
        return redirectTo(USER_REQUESTS + USER_BY_BOSS);
    }
}
