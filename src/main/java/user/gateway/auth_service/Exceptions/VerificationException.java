package user.gateway.auth_service.Exceptions;

import user.gateway.auth_service.Enums.ExceptionsEnum;

public class VerificationException extends Exception {
        
    public String code;

    public VerificationException(ExceptionsEnum exception) {
        super(exception.getMessage());
        this.code = exception.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
