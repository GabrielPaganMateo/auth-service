package auth.papertrail.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import auth.papertrail.app.request.LoginRequest;
import auth.papertrail.app.response.AuthResponse;
import auth.papertrail.app.service.interfase.LoginService;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(HttpServletResponse servletResponse, @RequestBody LoginRequest request) {
        AuthResponse response = loginService.loginProcess(servletResponse, request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
