package user.gateway.auth_service.Entities;

import java.util.UUID;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Data;

@Data
public class User {

    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String password;
    
}
