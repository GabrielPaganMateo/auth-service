package auth.papertrail.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import auth.papertrail.app.request.VerifyRequest;
import auth.papertrail.app.response.AuthResponse;
import auth.papertrail.app.service.interfase.VerificationService;

@RestController
public class VerifyController {

    private final VerificationService verificationService;

    @Autowired
    public VerifyController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }
    // With the current logic, in the end you cannot respond with ResponseEntity, you would have to redirect user to frontend. 
    @GetMapping("/verify/{token}")
    public ResponseEntity<AuthResponse> verify(@PathVariable String token) {
        AuthResponse response = verificationService.verificationProcess(new VerifyRequest(token));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
