package user.gateway.auth_service.Services;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import user.gateway.auth_service.Constants.MapKeys;
import user.gateway.auth_service.Entities.User;
import user.gateway.auth_service.Exceptions.InvalidEmailException;
import user.gateway.auth_service.Exceptions.RegisteredUserException;
import user.gateway.auth_service.Feigns.UserFeign;
import user.gateway.auth_service.Requests.RegisterRequest;

@Service
public class UserService {

    @Autowired
    private UserFeign userFeign;

    @Autowired 
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(RegisterRequest registerRequest) throws InvalidEmailException, RegisteredUserException {
        User user = mapToUser(registerRequest);
        isUserRegistered(user);
        return userFeign.registerUser(user);
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

    private void isUserRegistered(User user) throws RegisteredUserException {
        if(userFeign.isUserRegistered(user).get(MapKeys.IS_REGISTERED) == false) {
            return;
        };
        throw new RegisteredUserException();
    } 

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
