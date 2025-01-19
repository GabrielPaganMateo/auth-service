package user.gateway.auth_service.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import user.gateway.auth_service.Constants.UrlPaths;
import user.gateway.auth_service.Exceptions.InvalidEmailException;
import user.gateway.auth_service.Exceptions.RegisteredUserException;
import user.gateway.auth_service.Exceptions.SaveUserException;
import user.gateway.auth_service.Requests.RegisterRequest;
import user.gateway.auth_service.Responses.RegisterResponse;
import user.gateway.auth_service.Services.UserService;

@RestController
public class RegisterController {

    @Autowired
    private UserService userService;

    @PostMapping(UrlPaths.REGISTER)
    public ResponseEntity<RegisterResponse> register(@Valid @RequestBody RegisterRequest registerRequest) throws InvalidEmailException, RegisteredUserException, SaveUserException {
        return new ResponseEntity<RegisterResponse>(userService.registerUser(registerRequest), HttpStatus.CREATED);
    }

}
