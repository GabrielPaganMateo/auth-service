package auth.papertrail.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import auth.papertrail.app.request.VerifyRequest;
import auth.papertrail.app.response.AuthResponse;
import auth.papertrail.app.service.interfase.RedirectService;
import auth.papertrail.app.service.interfase.VerificationService;

@RestController
public class VerifyController {

    private final VerificationService verificationService;

    private final RedirectService redirectService;

    @Autowired
    public VerifyController(VerificationService verificationService, RedirectService redirectService) {
        this.verificationService = verificationService;
        this.redirectService = redirectService;
    }
    
    @GetMapping("/verify/{token}")
    public ModelAndView verify(@PathVariable String token, ModelMap model) {
        AuthResponse response = verificationService.verificationProcess(new VerifyRequest(token));
        return redirectService.verifyRedirect(response);
    }

}
