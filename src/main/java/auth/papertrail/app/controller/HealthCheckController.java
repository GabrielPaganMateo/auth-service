package auth.papertrail.app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import auth.papertrail.app.dto.Details;
import auth.papertrail.app.enumerator.ResponseCode;
import auth.papertrail.app.response.AuthResponse;

@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public ResponseEntity<AuthResponse> healthCheck() {
        AuthResponse response = new AuthResponse(ResponseCode.SERVICE_OK, Details.NONE);
        return ResponseEntity.ok(response);
    }

}
