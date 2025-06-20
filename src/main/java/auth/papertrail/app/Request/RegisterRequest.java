package auth.papertrail.app.Request;

public class RegisterRequest {
    // Inlude @NonNull , @NonBlank validations
    private String email;

    public RegisterRequest(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}