package auth.papertrail.app.enumerator;

public enum ExceptionType {

    INVALID_EMAIL("AUTH-FAIL-00", "Invalid email format"),
    USER_EXISTS("AUTH-FAIL-01", "User exists"),
    USER_UNVERIFIED("AUTH-FAIL-02", "User is unverified"),
    EXPIRED_TOKEN("AUTH-FAIL-03", "Token is expired"),
    INVALID_TOKEN("AUTH-FAIL-04", "Token is invalid"),
    USER_NOT_FOUND("AUTH-FAIL-05", "User does not exist"),
    INVALID_HEADER("AUTH-FAIL-06", "Missing or invalid Authorization header"),
    USER_CONFIRMED("AUTH-FAIL-07", "User is confirmed"),
    USER_WITHOUT_OTP("AUTH-FAIL-08", "Code does not exist for this user"),
    EXPIRED_OTP("AUTH-FAIL-09", "Expired verification code"),
    INVALID_OTP("AUTH-FAIL-10", "Invalid verification code"),
    INVALID_CREDENTIALS("AUTH-FAIL-11", "Invalid Credentials"),
    ERROR("AUTH-FAIL-99", "Server Error");

    private String code;
    private String message;

    ExceptionType(String code, String message) {
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
