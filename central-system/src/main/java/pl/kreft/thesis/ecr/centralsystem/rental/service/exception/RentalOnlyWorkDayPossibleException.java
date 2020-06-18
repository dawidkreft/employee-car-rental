package pl.kreft.thesis.ecr.centralsystem.rental.service.exception;

import pl.kreft.thesis.ecr.centralsystem.exception.EcrExceptionService;
import pl.kreft.thesis.ecr.centralsystem.exception.ErrorMessage;

public class RentalOnlyWorkDayPossibleException extends EcrExceptionService {
    public RentalOnlyWorkDayPossibleException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}