package auth.papertrail.app.service;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auth.papertrail.app.Request.RegisterRequest;
import auth.papertrail.app.Response.RegisterResponse;
import auth.papertrail.app.entity.EndUser;
import auth.papertrail.app.exception.InvalidEmailException;
import auth.papertrail.app.exception.UserExistsException;
import auth.papertrail.app.repository.UserRepository;
import auth.papertrail.app.service.interfase.EmailService;

@Service
public class RegisterService {

    private final UserRepository userRepository;

    private final EmailService emailService;

    @Autowired
    public RegisterService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public RegisterResponse registrationProcess(RegisterRequest request) {
        validateEmailFormat(request.getEmail());
        checkUserAlreadyExists(request.getEmail());
        sendVerificationCode(request.getEmail());
        return new RegisterResponse("SUCCESS");
    }

    private void validateEmailFormat(String email) {
        if(EmailValidator.getInstance().isValid(email) == false) {
            throw new InvalidEmailException();
        }
    }

    private void checkUserAlreadyExists(String email) {
       EndUser user = userRepository.findByEmail(email);
        if (user != null) { 
            if (user.getStatus().getCode() == 1) {
                throw new UserExistsException();
            } else if (user.getStatus().getCode() == 0) {
                // user exists but is unverified. redirect to verification
            }
        }
    }

    private void sendVerificationCode(String email) {
        emailService.sendVerificationEmail(email);
    }
}
