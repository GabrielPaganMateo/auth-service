package auth.papertrail.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import auth.papertrail.app.exception.AuthException;
import auth.papertrail.app.exception.UserUnverifiedException;
import auth.papertrail.app.response.ExceptionResponse;


@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(AuthException.class)
    protected ResponseEntity<ExceptionResponse> handleAuthException(AuthException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserUnverifiedException.class)
    protected ResponseEntity<ExceptionResponse> handleUserUnverifiedException(UserUnverifiedException e) {
        String messageWithEmail = e.getMessage() + " " + e.getEmail();
        return new ResponseEntity<>(new ExceptionResponse(e.getCode(), messageWithEmail), HttpStatus.BAD_REQUEST);
    }

}
