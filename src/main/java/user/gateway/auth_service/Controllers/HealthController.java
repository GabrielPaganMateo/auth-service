package user.gateway.auth_service.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import user.gateway.auth_service.Configurations.ApplicationProperties;
import user.gateway.auth_service.Constants.UrlPaths;
import user.gateway.auth_service.Responses.HealthResponse;

@RestController
public class HealthController {

    @Autowired
    ApplicationProperties applicationProperties;

    @GetMapping(UrlPaths.HEALTHCHECK)
    public ResponseEntity<HealthResponse> checkHealth() {
        return new ResponseEntity<>(new HealthResponse(applicationProperties.getVersion()), HttpStatus.OK);
    }
}
