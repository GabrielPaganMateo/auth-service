package auth.papertrail.app.enumerator;

public enum UserStatus {

    UNVERIFIED(0),
    VERIFIED(1);

    private int code;

    UserStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
