package user.gateway.auth_service.Services;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import feign.Response;
import user.gateway.auth_service.Constants.MapKeys;
import user.gateway.auth_service.Constants.RegisterMessages;
import user.gateway.auth_service.Entities.User;
import user.gateway.auth_service.Exceptions.InvalidEmailException;
import user.gateway.auth_service.Exceptions.RegisteredUserException;
import user.gateway.auth_service.Exceptions.SaveUserException;
import user.gateway.auth_service.Feigns.UserFeign;
import user.gateway.auth_service.Requests.RegisterRequest;
import user.gateway.auth_service.Responses.RegisterResponse;

@Service
public class UserService {

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private  JwtService jwtService;

    @Autowired 
    private BCryptPasswordEncoder passwordEncoder;

    public RegisterResponse registerUser(RegisterRequest registerRequest) throws InvalidEmailException, RegisteredUserException, SaveUserException {
        User user = mapToUser(registerRequest);
        return formatResponse(createJWT(saveUser(isUserRegistered(user))));
    }

    private User mapToUser(RegisterRequest registerRequest) throws InvalidEmailException {
        User user = new User(
            validateEmail(registerRequest.getEmail()),
            encodePassword(registerRequest.getPassword())
        );
        return user;
    }

    private String validateEmail(String email) throws InvalidEmailException {
        if (EmailValidator.getInstance().isValid(email)) {
            return email;
        };
        throw new InvalidEmailException();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

    private User isUserRegistered(User user) throws RegisteredUserException {
        if(userFeign.isUserRegistered(user).get(MapKeys.IS_REGISTERED) == false) {
            return user;
        };
        throw new RegisteredUserException();
    }

    private User saveUser(User user) throws SaveUserException {
        ResponseEntity<?> feignResponse = userFeign.registerUser(user); // this will never return the newly inserted item information, for that a getItem must be used
        if (feignResponse.getStatusCode() == HttpStatus.OK) {
            return user; // decided to use the same user object instance created from the beginning
        }
        throw new SaveUserException();
    }

    private String createJWT(User user) {
        return jwtService.generateToken(user);
    }

    private RegisterResponse formatResponse(String jwt) {
        String message = String.format(RegisterMessages.REGISTERED, jwtService.extractEmail(jwt));
        return new RegisterResponse(message, jwt);
    }

}
