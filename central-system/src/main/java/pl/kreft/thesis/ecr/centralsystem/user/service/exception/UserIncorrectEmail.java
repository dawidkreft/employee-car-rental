package pl.kreft.thesis.ecr.centralsystem.user.service.exception;

import pl.kreft.thesis.ecr.centralsystem.exception.EcrExceptionService;
import pl.kreft.thesis.ecr.centralsystem.exception.ErrorMessage;

public class UserIncorrectEmail extends EcrExceptionService {
    public UserIncorrectEmail(ErrorMessage errorMessage) {
        super(errorMessage);
    }
}
