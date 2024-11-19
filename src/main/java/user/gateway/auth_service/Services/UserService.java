package user.gateway.auth_service.Services;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import user.gateway.auth_service.Entities.User;
import user.gateway.auth_service.Exceptions.InvalidEmailException;
import user.gateway.auth_service.FeignClients.UserFeignClient;
import user.gateway.auth_service.Requests.RegisterRequest;

@Service
public class UserService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired 
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(RegisterRequest registerRequest) throws InvalidEmailException {
        User user = mapToUser(registerRequest);
        return userFeignClient.postUser(user);
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
        }
        throw new InvalidEmailException();
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
