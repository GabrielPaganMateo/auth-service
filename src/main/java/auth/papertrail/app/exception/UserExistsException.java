package auth.papertrail.app.exception;

import java.util.HashMap;

import auth.papertrail.app.enumerator.ExceptionCode;

public class UserExistsException extends AuthException {

    public UserExistsException() {
        super(ExceptionCode.USER_EXISTS.getCode(), ExceptionCode.USER_EXISTS.getMessage(), new HashMap<String, String>());
    }

}
