package auth.papertrail.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import auth.papertrail.app.annotation.AuthRequired;
import auth.papertrail.app.enumerator.TokenType;

@RestController
public class ConfirmationController {

    @AuthRequired(requires = TokenType.REGISTRATION)
    @PostMapping("/confirm")
    public ResponseEntity<String> confirm() {
        return new ResponseEntity<>("CONFIRMED", HttpStatus.OK);
    }
}
