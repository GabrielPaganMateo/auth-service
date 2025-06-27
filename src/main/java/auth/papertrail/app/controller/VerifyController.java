package auth.papertrail.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import auth.papertrail.app.request.VerifyRequest;
import auth.papertrail.app.service.interfase.JWTService;

@RestController
public class VerifyController {

    @Autowired
    JWTService jwtService;


    @GetMapping("/verify/{token}")
    public ResponseEntity<String> verify(@PathVariable String token) {
        VerifyRequest request = new VerifyRequest(token);
        return new ResponseEntity<>(jwtService.verifyToken(token).getIssuer(), HttpStatus.OK);
    }
}
