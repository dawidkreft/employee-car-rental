package pl.kreft.thesis.ecr.centralsystem.rental.service.exception;

import pl.kreft.thesis.ecr.centralsystem.exception.EcrExceptionService;
import pl.kreft.thesis.ecr.centralsystem.exception.ErrorMessage;

public class IncorrectCurrentFuelException extends EcrExceptionService {
    public IncorrectCurrentFuelException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}