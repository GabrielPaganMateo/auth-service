package user.gateway.auth_service.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import user.gateway.auth_service.Constants.UrlPaths;
import user.gateway.auth_service.Responses.VerifyResponse;
import user.gateway.auth_service.Services.VerifyService;

@RestController
public class VerifyController {

    @Autowired
    VerifyService verifyService;

    @GetMapping(UrlPaths.VERIFY)
    public ResponseEntity<VerifyResponse> verifyEmail(@RequestParam String token) throws Exception {
        return new ResponseEntity<VerifyResponse>(verifyService.verifyUser(token), HttpStatus.OK);
    }
}
