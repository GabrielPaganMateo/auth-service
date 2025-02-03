package user.gateway.auth_service.Exceptions;

import org.springframework.boot.autoconfigure.security.saml2.Saml2RelyingPartyProperties.AssertingParty.Verification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import user.gateway.auth_service.Responses.ExceptionResponse;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { RegisteredUserException.class })
    protected ResponseEntity<ExceptionResponse> handleRegisteredUserException(RegisteredUserException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getCode(), exception.getMessage()), null, HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(value = { InvalidEmailException.class })
    protected ResponseEntity<ExceptionResponse> handleInvalidEmailException(InvalidEmailException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getCode(), exception.getMessage()), null, HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(value = { SaveUserException.class })
    protected ResponseEntity<ExceptionResponse> handleSaveUserException(SaveUserException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getCode(), exception.getMessage()), null, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler(value = { VerificationException.class })
    protected ResponseEntity<ExceptionResponse> handleVerificationException(VerificationException exception) {
        return new ResponseEntity<>(new ExceptionResponse(exception.getCode(), exception.getMessage()), null, HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    
}
