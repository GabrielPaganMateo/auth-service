package auth.papertrail.app.enumerator;

public enum UserStatus {
    // USE ONLY TWO STATUS 
    // REGISTERED (set through Register Controller)
    // CONFIRMED (set through Verify and ConfrimController)

    REGISTERED(0),
    CONFIRMED(1);

    private int code;

    UserStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
