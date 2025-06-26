package auth.papertrail.app.enumerator;

public enum ResponseCode {

    REGISTER_OK("AUTH-SUCCESS-00", "User registered, please verify");

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
