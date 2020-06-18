package pl.kreft.thesis.ecr.centralsystem.rental.service.exception;

import pl.kreft.thesis.ecr.centralsystem.exception.EcrExceptionService;
import pl.kreft.thesis.ecr.centralsystem.exception.ErrorMessage;

public class RentalCarException extends EcrExceptionService {
    public RentalCarException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
