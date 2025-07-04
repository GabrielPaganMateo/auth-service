package auth.papertrail.app.request;

public class VerifyRequest {

    public String token;

    public VerifyRequest(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
