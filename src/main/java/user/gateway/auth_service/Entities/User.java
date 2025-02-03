package user.gateway.auth_service.Entities;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import user.gateway.auth_service.Enums.VerifiedStatusEnum;

@JsonInclude(Include.NON_NULL)
public class User {
    
    @Id
    private UUID id;

    private String email;

    private String password;

    private VerifiedStatusEnum verified;

    public User() {

    }

    public User(String email, String password, VerifiedStatusEnum verified) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.password = password;
        this.verified = verified;
    }

    public User(String id, String email, String password, VerifiedStatusEnum verified) {
        this.id = UUID.fromString(id);
        this.email = email;
        this.password = password;
        this.verified = verified;
    }

    public UUID getId() {
        return id;
    }

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

    public VerifiedStatusEnum getVerified() {
        return verified;
    }

    public void setVerified(VerifiedStatusEnum verified) {
        this.verified = verified;
    }
}
