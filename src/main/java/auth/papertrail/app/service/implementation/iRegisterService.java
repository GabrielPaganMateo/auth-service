package auth.papertrail.app.service.implementation;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auth.papertrail.app.entity.AuthInfo;
import auth.papertrail.app.entity.EndUser;
import auth.papertrail.app.enumerator.UserStatus;
import auth.papertrail.app.exception.InvalidEmailException;
import auth.papertrail.app.exception.UserExistsException;
import auth.papertrail.app.repository.UserRepository;
import auth.papertrail.app.request.RegisterRequest;
import auth.papertrail.app.response.RegisterResponse;
import auth.papertrail.app.service.interfase.EmailService;
import auth.papertrail.app.service.interfase.RegisterService;

@Service
public class iRegisterService implements RegisterService {

    private final UserRepository userRepository;

    private final EmailService emailService;

    @Autowired
    public iRegisterService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public RegisterResponse registrationProcess(RegisterRequest request) {
        String email = request.getEmail();
        validateEmailFormat(email);
        checkUserAlreadyExists(email);
        saveUserWithUnverifiedStatus(email);
        sendVerificationLink(email);
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
            if (user.getAuthInfo().getUserStatus().getCode() == 1) {
                throw new UserExistsException();
            } else if (user.getAuthInfo().getUserStatus().getCode() == 0) {
                // user exists but is unverified. redirect to verification
            }
        }
    }

    private void sendVerificationLink(String email) {
        emailService.sendVerificationEmail(email);
    }

    private void saveUserWithUnverifiedStatus(String email) {
        EndUser user = new EndUser(email);
        AuthInfo info = new AuthInfo(user, UserStatus.UNVERIFIED);
        user.setAuthInfo(info);
        userRepository.save(user);
    }
}
