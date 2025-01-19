package user.gateway.auth_service.Exceptions;

import user.gateway.auth_service.Enums.ExceptionsEnum;

public class RegisteredUserException extends Exception {

    private String code;

    public RegisteredUserException() {
        super(ExceptionsEnum.IS_REGISTERED.getMessage());
        this.code = ExceptionsEnum.IS_REGISTERED.getCode();

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
