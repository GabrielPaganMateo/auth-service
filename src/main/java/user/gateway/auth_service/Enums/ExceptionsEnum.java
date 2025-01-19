package user.gateway.auth_service.Enums;

public enum ExceptionsEnum {

    IS_REGISTERED("AUTH-00", "User is already registered"), INVALID_EMAIL("AUTH-01", "Invalid email address"),
    USER_SAVE_FAIL("AUTH-02", "Failed to save user info");

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
