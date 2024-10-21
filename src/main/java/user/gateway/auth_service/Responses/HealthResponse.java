package user.gateway.auth_service.Responses;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import user.gateway.auth_service.Constants.HealthMessages;

@Getter
@Setter
@Component
public class HealthResponse {

    @Value(HealthMessages.SERVICE_UP)
    private String status;

    @Value(HealthMessages.VERSION)
    private String version;

}
