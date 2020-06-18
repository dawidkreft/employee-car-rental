package pl.kreft.thesis.ecr.centralsystem.exception;

import lombok.Getter;

@Getter
public class EcrExceptionService extends RuntimeException {

    private final ErrorMessage errorMessage;

    public EcrExceptionService(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
        this.errorMessage = errorMessage;
    }
}

