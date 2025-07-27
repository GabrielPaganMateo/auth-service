package auth.papertrail.app.controller.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import auth.papertrail.app.exception.AuthException;
import auth.papertrail.app.exception.VerifyException;
import auth.papertrail.app.response.ExceptionResponse;
import auth.papertrail.app.service.interfase.RedirectService;


@RestControllerAdvice
public class ControllerExceptionHandler {

    private final Logger log = LoggerFactory.getLogger(ControllerExceptionHandler.class);

    private final RedirectService redirectService;

    @Autowired
    public ControllerExceptionHandler(RedirectService redirectService) {
        this.redirectService = redirectService;
    }

    @ExceptionHandler(AuthException.class)
    protected ResponseEntity<ExceptionResponse> handleAuthException(AuthException e) {
        printException(e);
        return new ResponseEntity<>(new ExceptionResponse(e.getExceptionType(), e.getDetails()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VerifyException.class)
    protected ModelAndView handleAuthException(VerifyException e) {
        printException(e);
        return redirectService.exceptionRedirect(e);
    }

    private void printException(Exception e) {
        log.error(e.getMessage(), e.getCause());
        e.printStackTrace();
    }

}
