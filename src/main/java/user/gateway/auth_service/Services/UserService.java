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

    public User getUser(String id) {
        return userFeignClient.getUser(id);
    }

    public User registerUser(RegisterRequest registerRequest) throws InvalidEmailException {
        if (validEmail(registerRequest.getEmail()) == true) {
            User user = mapToUser(registerRequest);
            return userFeignClient.postUser(user);
        }
        throw new InvalidEmailException();
    }

    private User mapToUser(RegisterRequest registerRequest) throws InvalidEmailException {
        User user = new User();
        user.setEmail(registerRequest.getEmail());
        user.setPassword(encodePassword(registerRequest.getPassword()));
        return user;
    }

    private boolean validEmail(String email) {
        if (EmailValidator.getInstance().isValid(email)) {
            return true;
        }
        return false;
    }

    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }

}
