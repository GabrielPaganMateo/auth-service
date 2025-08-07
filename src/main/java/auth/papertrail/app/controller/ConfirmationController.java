package auth.papertrail.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import auth.papertrail.app.annotation.AuthRequired;
import auth.papertrail.app.enumerator.TokenType;
import auth.papertrail.app.request.ConfirmRequest;
import auth.papertrail.app.response.AuthResponse;
import auth.papertrail.app.service.interfase.ConfirmationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class ConfirmationController {

    private final ConfirmationService confirmationService;

    @Autowired
    public ConfirmationController(ConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
    }

    
    @AuthRequired(requires = TokenType.CONFIRMATION)
    @PostMapping("/confirm")
    public ResponseEntity<AuthResponse> confirm(HttpServletRequest servletRequest, HttpServletResponse servletResponse, @RequestBody ConfirmRequest request) {
        AuthResponse response = confirmationService.confirmationProcess(servletRequest, request, servletResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
