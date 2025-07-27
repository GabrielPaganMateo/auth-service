package auth.papertrail.app.enumerator;

public enum ResponseCode {

    SERVICE_OK("AUTH-SUCCESS-00", "Service is up!"),
    REGISTER_OK("AUTH-SUCCESS-01", "User registered, please verify"),
    VERIFY_OK("AUTH-SUCCESS-02", "User verified, please confirm"),
    CONFIRM_OK("AUTH-SUCCESS-03", "User confirmed"),
    LOGIN_OK("AUTH-SUCCESS-04", "User logged in");

    private String code;
    private String message;

    private ResponseCode(String code, String message) {
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
