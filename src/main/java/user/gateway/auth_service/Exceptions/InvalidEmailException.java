package user.gateway.auth_service.Exceptions;

import user.gateway.auth_service.Enums.ExceptionsEnum;

public class InvalidEmailException extends Exception {

    private String code;

    public InvalidEmailException() {
        super(ExceptionsEnum.INVALID_EMAIL.getMessage());
        this.code = ExceptionsEnum.INVALID_EMAIL.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
