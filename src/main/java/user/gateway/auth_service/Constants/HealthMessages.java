package user.gateway.auth_service.Constants;

import org.springframework.stereotype.Component;

@Component
public class HealthMessages {
    
    public static final String SERVICE_UP = "Auth-Service is UP!";

    public static final String VERSION = "${application.version}";
    
}
