package user.gateway.auth_service.Exceptions;

import user.gateway.auth_service.Constants.ExceptionMessages;

public class RegisteredUserException extends Exception {

    public RegisteredUserException() {
        super(ExceptionMessages.REGISTERED_USER);
    }
}
