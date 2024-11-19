package user.gateway.auth_service.Exceptions;

import user.gateway.auth_service.Constants.ExceptionMessages;

public class InvalidEmailException extends Exception {

    public InvalidEmailException() {
        super(ExceptionMessages.INVALID_EMAIL);
    }
}
