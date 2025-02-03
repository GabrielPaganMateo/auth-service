package user.gateway.auth_service.Enums;

public enum ExceptionsEnum {

    IS_REGISTERED("AUTH-00", "User is already registered"), INVALID_EMAIL("AUTH-01", "Invalid email address"),
    USER_SAVE_FAIL("AUTH-02", "Failed to save user info"), INCORRECT_TOKEN_TYPE("AUTH-03", "Incorrect token type"),
    EXPIRED_TOKEN("AUTH-04", "Verification code expired"), USER_LOOKUP_FAILED("AUTH-05", "Get user item failed"),
    EMAIL_MATCH_FAIL("AUTH-06", "User not associated to email"), IS_VERIFIED("AUTH-07", "User is already verified"),
    VERIFY_FAILED("AUTH-07", "Change usser verification status failed");

    private final String code;
    private final String message;

    ExceptionsEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
