package pl.kreft.thesis.ecr.centralsystem.rental.service.exception;

import pl.kreft.thesis.ecr.centralsystem.exception.EcrExceptionService;
import pl.kreft.thesis.ecr.centralsystem.exception.ErrorMessage;

public class IncorrectDistanceTraveledException extends EcrExceptionService {
    public IncorrectDistanceTraveledException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}