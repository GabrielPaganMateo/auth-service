package auth.papertrail.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import auth.papertrail.app.annotation.AuthRequired;
import auth.papertrail.app.enumerator.TokenType;
import auth.papertrail.app.request.VerifyRequestV2;
import auth.papertrail.app.response.AuthResponse;
import auth.papertrail.app.service.interfase.VerificationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
public class VerifyController {

    private final VerificationService verificationService;

    @Autowired
    public VerifyController(VerificationService verificationService) {
        this.verificationService = verificationService;
    }
    
    // @Deprecated
    // @GetMapping("/verify/{token}")
    // public ModelAndView verify(@PathVariable String token, ModelMap model) {
    //     AuthResponse response = verificationService.verificationProcess(new VerifyRequest(token));
    //     return redirectService.verifyRedirect(response);
    // }

    @AuthRequired(requires = TokenType.VERIFICATION)
    @PostMapping("/verify")
    public ResponseEntity<AuthResponse> verify(@RequestBody VerifyRequestV2 request, HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        AuthResponse response = verificationService.verificationProcess(request, servletRequest, servletResponse);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @AuthRequired(requires = TokenType.VERIFICATION)
    @PostMapping("/verify/resend-code")
    public ResponseEntity<AuthResponse> resendVerificationCode(HttpServletRequest servletRequest) {
        AuthResponse response = verificationService.resendVerificationCode(servletRequest);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
