package auth.papertrail.app.service.implementation;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import auth.papertrail.app.dto.Details;
import auth.papertrail.app.entity.EndUser;
import auth.papertrail.app.entity.OneTimePasscode;
import auth.papertrail.app.enumerator.ExceptionType;
import auth.papertrail.app.exception.AuthException;
import auth.papertrail.app.repository.OTPRepository;
import auth.papertrail.app.service.interfase.EmailService;
import auth.papertrail.app.service.interfase.OTPService;


@Service
public class iOTPService implements OTPService {

    @Value("${auth-service.otp.time-to-live(minutes)}")
    private int otpExpiration;

    private final OTPRepository otpRepository;

    private final EmailService emailService;

    @Autowired
    public iOTPService(OTPRepository otpRepository, EmailService emailService) {
        this.otpRepository = otpRepository;
        this.emailService = emailService;
    }

    public int generateOTP(EndUser user) {
        OneTimePasscode otp = new OneTimePasscode(user, generateCode());
        otpRepository.save(otp);
        return otp.getCode();
    }

    public OneTimePasscode getUserOTP(EndUser user) {
        return otpRepository.findByEndUserId(user.getId()).orElseThrow(() -> new AuthException(ExceptionType.USER_WITHOUT_OTP, Details.NONE));
    } 

    public boolean isExpiredOTP(OneTimePasscode otp) {
        Instant now = new Date().toInstant();
        if (now.isAfter(otp.getCreatedDate().toInstant().plus(Duration.ofMinutes(otpExpiration)))) {
            return true;
        }
        return false;
    }

    public int updateOTP(OneTimePasscode otp) {
        otp.setCreatedDate(new Date());
        otp.setCode(generateCode());
        otpRepository.save(otp);
        return otp.getCode();
    }

    private int generateCode() {
        SecureRandom random = new SecureRandom();
        return random.nextInt(900000) + 100000;
    }

    public void sendNewOTP(EndUser endUser) {
        OneTimePasscode otp = getUserOTP(endUser);
        if (isExpiredOTP(otp)) {
            emailService.sendVerificationEmail(endUser, updateOTP(otp));
        }
    }

}
