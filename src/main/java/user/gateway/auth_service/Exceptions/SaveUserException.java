package user.gateway.auth_service.Exceptions;

import user.gateway.auth_service.Enums.ExceptionsEnum;

public class SaveUserException extends Exception {
    
    public String code;

    public SaveUserException() {
        super(ExceptionsEnum.USER_SAVE_FAIL.getMessage());
        this.code = ExceptionsEnum.USER_SAVE_FAIL.getCode();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    
}
