package pl.kreft.thesis.ecr.centralsystem.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import static pl.kreft.thesis.ecr.centralsystem.common.ResponsePageMappingInfo.EXCEPTION;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView exception(final Throwable throwable, final Model model) {
        log.error("Exception during execution of SpringSecurity application", throwable);
        ModelAndView modelAndView = new ModelAndView(EXCEPTION);
        String errorMessage = (throwable != null ? throwable.getMessage() : "Nie znany błąd");
        modelAndView.addObject("errorMessage", errorMessage);
        return modelAndView;
    }
}
