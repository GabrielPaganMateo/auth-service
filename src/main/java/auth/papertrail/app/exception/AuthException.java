package auth.papertrail.app.exception;

import java.util.Map;

import auth.papertrail.app.enumerator.ExceptionType;

public class AuthException extends RuntimeException {

    private ExceptionType exceptionType;
    private Map<String, String> details;

    public AuthException(ExceptionType exceptionType, Map<String, String> details) {
        super(exceptionType.getMessage());
        this.exceptionType = exceptionType;
        this.details = details;
    }

    public ExceptionType getExceptionType() {
        return exceptionType;
    }

    public Map<String, String> getDetails() {
        return details;
    }

}
