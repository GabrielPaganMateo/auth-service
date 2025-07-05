package auth.papertrail.app.exception;

import java.util.Map;

import auth.papertrail.app.enumerator.ExceptionType;

public class VerifyException extends AuthException {

    public VerifyException(ExceptionType exceptionType, Map<String, String> details) {
        super(exceptionType, details);
    }

}
