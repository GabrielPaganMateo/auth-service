package auth.papertrail.app.exception;

import java.util.Map;

public class AuthException extends RuntimeException {

    private String code;
    private Map<String, String> details;

    AuthException(String code, String message, Map<String, String> details) {
        super(message);
        this.code = code;
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public Map<String, String> getDetails() {
        return details;
    }

}
