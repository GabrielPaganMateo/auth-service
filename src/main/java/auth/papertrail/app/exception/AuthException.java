package auth.papertrail.app.exception;

public class AuthException extends RuntimeException {

    private String code;

    AuthException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
