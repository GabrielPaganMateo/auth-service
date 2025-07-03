package auth.papertrail.app.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import auth.papertrail.app.exception.AuthException;
import auth.papertrail.app.response.ExceptionResponse;


@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(AuthException.class)
    protected ResponseEntity<ExceptionResponse> handleAuthException(AuthException e) {
        return new ResponseEntity<>(new ExceptionResponse(e.getExceptionType(), e.getDetails()), HttpStatus.BAD_REQUEST);
    }

}
