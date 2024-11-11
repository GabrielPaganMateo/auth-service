package user.gateway.auth_service.Requests;

import lombok.Data;

@Data
public class RegisterRequest {

    private String identity;

    private String password;

}
