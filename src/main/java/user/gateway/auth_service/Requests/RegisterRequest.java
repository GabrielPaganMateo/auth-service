package user.gateway.auth_service.Requests;

import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {

    @NotBlank(message = "Email must be given") // Cant be used if email or phone identity option is available
    private String email;

    // private phone;

    @NotBlank(message = "Password must be given") // Cant 
    private String password;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
