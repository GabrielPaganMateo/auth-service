package auth.papertrail.app.exception;

import java.util.Map;

import auth.papertrail.app.enumerator.ExceptionType;

public class AuthException extends RuntimeException {

    private String code;
    private Map<String, String> details;

    public AuthException(ExceptionType exceptionType, Map<String, String> details) {
        super(exceptionType.getMessage());
        this.code = exceptionType.getCode();
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public Map<String, String> getDetails() {
        return details;
    }

}
