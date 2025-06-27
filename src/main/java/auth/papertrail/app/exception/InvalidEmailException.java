package auth.papertrail.app.exception;

import java.util.Map;

import auth.papertrail.app.enumerator.ExceptionCode;

public class InvalidEmailException extends AuthException {

    public InvalidEmailException(String email) {
        super(ExceptionCode.INVALID_EMAIL.getCode(), ExceptionCode.INVALID_EMAIL.getMessage(), Map.of("email", email));
    }

}
