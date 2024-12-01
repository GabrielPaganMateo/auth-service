package user.gateway.auth_service.Exceptions;

import user.gateway.auth_service.Constants.ExceptionMessages;

public class RegisteredUserException extends Exception {

    private String code;

    public RegisteredUserException() {
        super(ExceptionMessages.REGISTERED_USER);
        this.code = "AUTH_0000";

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
