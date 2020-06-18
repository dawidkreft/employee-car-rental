package pl.kreft.thesis.ecr.centralsystem.rental.service.exception;

import pl.kreft.thesis.ecr.centralsystem.exception.EcrExceptionService;
import pl.kreft.thesis.ecr.centralsystem.exception.ErrorMessage;

public class RentalDateEndIsBeforeStartException extends EcrExceptionService {
    public RentalDateEndIsBeforeStartException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}