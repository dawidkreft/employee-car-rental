package pl.kreft.thesis.ecr.centralsystem.rental.service.exception;

import pl.kreft.thesis.ecr.centralsystem.exception.EcrExceptionService;
import pl.kreft.thesis.ecr.centralsystem.exception.ErrorMessage;

public class RentalByEmployeeException extends EcrExceptionService {
    public RentalByEmployeeException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
