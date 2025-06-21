package auth.papertrail.app.exception;

import auth.papertrail.app.enumerator.ExceptionCode;

public class UserExistsException extends AuthException {

    public UserExistsException() {
        super(ExceptionCode.USER_EXISTS.getCode(), ExceptionCode.USER_EXISTS.getMessage());
    }

}
