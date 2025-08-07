package auth.papertrail.app.service.implementation;

import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import auth.papertrail.app.constants.MapKeys;
import auth.papertrail.app.dto.Details;
import auth.papertrail.app.entity.EndUser;
import auth.papertrail.app.entity.OneTimePasscode;
import auth.papertrail.app.enumerator.ExceptionType;
import auth.papertrail.app.enumerator.ResponseCode;
import auth.papertrail.app.enumerator.TokenType;
import auth.papertrail.app.enumerator.UserStatus;
import auth.papertrail.app.exception.AuthException;
import auth.papertrail.app.exception.VerifyException;
import auth.papertrail.app.repository.UserRepository;
import auth.papertrail.app.request.VerifyRequestV2;
import auth.papertrail.app.response.AuthResponse;
import auth.papertrail.app.service.interfase.JWTService;
import auth.papertrail.app.service.interfase.OTPService;
import auth.papertrail.app.service.interfase.VerificationService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class iVerificationServiceV2 implements VerificationService {
    
    private final JWTService jwtService;

    private final UserRepository userRepository;

    private final OTPService otpService;

    @Autowired
    public iVerificationServiceV2(JWTService jwtService, UserRepository userRepository, OTPService otpService) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.otpService = otpService;
    }

    public AuthResponse verificationProcess(VerifyRequestV2 request, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        String id = getUserIdfromRequest(servletRequest);
        EndUser user = getUser(id);
        checkUserAlreadyVerified(user);
        verifyOTP(request.getCode(), user);
        String token = generateRegistrationToken(user);
        setAuthHeader(servletResponse, token);
        return new AuthResponse(ResponseCode.VERIFY_OK, Map.of("email", user.getEmail()));
    }

    private void checkUserAlreadyVerified(EndUser user) {
         if (user.getAuthInfo().getUserStatus() == UserStatus.CONFIRMED) {
            throw new VerifyException(ExceptionType.USER_EXISTS, Details.email(user.getEmail()));
        }
    }

    @Transactional(readOnly = true) 
    EndUser getUser(String id) {
            return userRepository.findById(UUID.fromString(id)).orElseThrow(
                () -> new VerifyException(ExceptionType.USER_NOT_FOUND, Details.NONE)
            );
    }

    private String generateRegistrationToken(EndUser user) {
        return jwtService.createToken(user, TokenType.CONFIRMATION, Details.NONE);
    }

    private String getUserIdfromRequest(HttpServletRequest request) {
        if (request.getAttribute(MapKeys.ID) instanceof String) {
            return (String) request.getAttribute(MapKeys.ID);
        }
        throw new AuthException(ExceptionType.ERROR, Details.NONE);
    }

    private void setAuthHeader(HttpServletResponse servletResponse, String token) {
        servletResponse.addHeader(MapKeys.AUTH_HEADER, MapKeys.BEARER + token);
    }

    private void verifyOTP(int code, EndUser user) {
        OneTimePasscode otp = otpService.getUserOTP(user);
        if (otp.getCode() == code) {
            if (otpService.isExpiredOTP(otp)) {
                throw new AuthException(ExceptionType.EXPIRED_OTP, Details.NONE);
            }
            return;
        }
        throw new AuthException(ExceptionType.INVALID_OTP, Details.NONE);
    }

    @Override
    public AuthResponse resendVerificationCode(HttpServletRequest servletRequest) {
        String id = getUserIdfromRequest(servletRequest);
        EndUser user = getUser(id);
        otpService.sendNewOTP(user);
        return new AuthResponse(ResponseCode.RESEND_OK,  Details.NONE);
    }
}
