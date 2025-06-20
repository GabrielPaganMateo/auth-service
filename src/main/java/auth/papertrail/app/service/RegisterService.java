package auth.papertrail.app.service;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import auth.papertrail.app.Request.RegisterRequest;
import auth.papertrail.app.Response.RegisterResponse;
import auth.papertrail.app.exception.InvalidEmailException;

@Service
public class RegisterService {

    public RegisterResponse registrationProcess(RegisterRequest request) {
        validateEmail(request.getEmail());
        return new RegisterResponse("SUCCESS");
    }

    private void validateEmail(String email) {
        if(EmailValidator.getInstance().isValid(email) == false) {
            throw new InvalidEmailException();
        }
    }
}
