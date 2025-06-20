package auth.papertrail.app.exception;

import auth.papertrail.app.enumerator.ExceptionCode;

public class InvalidEmailException extends AuthException {

    public InvalidEmailException() {
        super(ExceptionCode.INVALID_EMAIL.getCode(), ExceptionCode.INVALID_EMAIL.getMessage());
    }

}
