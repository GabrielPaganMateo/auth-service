package user.gateway.auth_service.Exceptions;

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
    protected ResponseEntity<Object> registerException(RegisteredUserException exception) {
        // return handleExceptionInternal(ex, "customer error Message", new HttpHeaders(), HttpStatusCode.valueOf(420), request);
        return new ResponseEntity<>(new ExceptionResponse(exception.getCode(), exception.getMessage()), new HttpHeaders(), HttpStatus.BAD_REQUEST.value());
    }
}
