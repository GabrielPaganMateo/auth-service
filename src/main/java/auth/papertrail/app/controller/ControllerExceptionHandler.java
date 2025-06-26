package auth.papertrail.app.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import auth.papertrail.app.exception.AuthException;
import auth.papertrail.app.response.RegisterResponse;


@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(AuthException.class)
    protected ResponseEntity<RegisterResponse> handleAuthException(AuthException e) {
        return new ResponseEntity<>(new RegisterResponse(e.getCode(), e.getMessage(), e.getDetails()), HttpStatus.BAD_REQUEST);
    }

}
