package auth.papertrail.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import auth.papertrail.app.request.RegisterRequest;
import auth.papertrail.app.response.RegisterResponse;
import auth.papertrail.app.service.interfase.RegisterService;

// MUST ADD CONTROLLER ADVICE
@RestController
public class RegisterController {

    private final RegisterService registerService;

    @Autowired
    public RegisterController(RegisterService registerService) {
        this.registerService = registerService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        RegisterResponse response = registerService.registrationProcess(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
