package auth.papertrail.app.enumerator;

public enum TokenType {

    VERIFICATION(0, 600l),
    CONFIRMATION(2, 600l),
    LOGIN(3, 900l);

    private int code;
    private long timeToLive;

    TokenType(int code, long timeToLive) {
        this.code = code;
        this.timeToLive = timeToLive;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }

}
