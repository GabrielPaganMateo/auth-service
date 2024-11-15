package user.gateway.auth_service.Responses;

import user.gateway.auth_service.Constants.HealthMessages;

public class HealthResponse {

    private String status;

    private String version;

    public HealthResponse(String version) {
        this.status = HealthMessages.SERVICE_UP;
        this.version = version;
    }

    public String getStatus() {
        return status;
    }

    public String getVersion() {
        return version;
    }
}
