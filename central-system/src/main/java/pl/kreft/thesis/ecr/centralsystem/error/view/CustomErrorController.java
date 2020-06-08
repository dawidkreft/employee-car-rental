package pl.kreft.thesis.ecr.centralsystem.error.view;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.ERROR_403_REQUEST;
import static pl.kreft.thesis.ecr.centralsystem.common.RequestPageMappingInfo.ERROR_REQUEST;
import static pl.kreft.thesis.ecr.centralsystem.common.ResponsePageMappingInfo.ERROR;
import static pl.kreft.thesis.ecr.centralsystem.common.ResponsePageMappingInfo.ERROR_403;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping(ERROR_REQUEST)
    public ModelAndView error() {
        return new ModelAndView(ERROR);
    }

    @GetMapping(ERROR_403_REQUEST)
    public ModelAndView error403() {
        return new ModelAndView(ERROR_403);
    }

    @Override
    public String getErrorPath() {
        return ERROR_REQUEST;
    }
}