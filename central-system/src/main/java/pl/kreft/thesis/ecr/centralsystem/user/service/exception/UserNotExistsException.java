package pl.kreft.thesis.ecr.centralsystem.user.service.exception;

import pl.kreft.thesis.ecr.centralsystem.exception.EcrExceptionService;
import pl.kreft.thesis.ecr.centralsystem.exception.ErrorMessage;

public class UserNotExistsException extends EcrExceptionService {
    public UserNotExistsException(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}