package auth.papertrail.app.enumerator;

public enum ExceptionCode {

    INVALID_EMAIL("AUTH-00", "Invalid email format"),
    USER_EXISTS("AUTH-01", "User already registered");

    private String code;
    private String message;

    ExceptionCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
