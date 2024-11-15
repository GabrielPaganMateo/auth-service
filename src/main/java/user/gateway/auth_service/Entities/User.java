package user.gateway.auth_service.Entities;

import java.util.UUID;

import org.springframework.data.annotation.Id;

public class User {
    
    @Id
    private UUID id;

    private String password;

    public User() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
    
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
