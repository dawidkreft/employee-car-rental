package pl.kreft.thesis.ecr.centralsystem.message.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.kreft.thesis.ecr.centralsystem.message.model.MessageDTO;
import pl.kreft.thesis.ecr.centralsystem.message.service.MessageService;
import pl.kreft.thesis.ecr.centralsystem.user.model.UserDetailsProvider;
import pl.kreft.thesis.ecr.centralsystem.user.service.UserService;

import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.MESSAGE_REQUEST;
import static pl.kreft.thesis.ecr.centralsystem.common.ResponsePageMappingInfo.MESSAGE_FORM;
import static pl.kreft.thesis.ecr.centralsystem.common.ResponsePageMappingInfo.MESSAGE_OK;

@RequestMapping(MESSAGE_REQUEST)
@Controller
public class MessageView {

    private final MessageService messageService;
    private final UserService userService;

    @Autowired
    public MessageView(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @GetMapping
    public String returnMessageForm(Model model) {
        model.addAttribute("admins", userService.getAdmins());
        model.addAttribute("messageDTO", new MessageDTO());
        return MESSAGE_FORM;
    }

    @PostMapping
    public String saveMessage(@AuthenticationPrincipal UserDetailsProvider user,
            @ModelAttribute MessageDTO messageDTO) {
        messageService.save(messageDTO, user.getId());
        return MESSAGE_OK;
    }
}
