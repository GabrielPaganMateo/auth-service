package auth.papertrail.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import auth.papertrail.app.request.LoginRequest;
import auth.papertrail.app.response.AuthResponse;

@RestController
public class LoginController {


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response;
        return new ResponseEntity<>(new AuthResponse(null, null), HttpStatus.OK);
    }

}
