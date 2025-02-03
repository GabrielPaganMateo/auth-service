package user.gateway.auth_service.Responses;

public class VerifyResponse {
    
    private String message;

    public VerifyResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
