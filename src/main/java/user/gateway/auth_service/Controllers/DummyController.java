package user.gateway.auth_service.Controllers;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import user.gateway.auth_service.Constants.UrlPaths;
import user.gateway.auth_service.Services.JwtService;

@RestController
public class DummyController {

    @Autowired
    JwtService jwtService;

    @GetMapping(UrlPaths.DUMMY)
    public ResponseEntity<Map<String, Object>> decryptJWT(@RequestParam String token, @RequestParam String email) {
        Map<String, Object> body = new HashMap<>();
        String id = jwtService.extractId(token);
        body.put("id", id);

        body.put("email", jwtService.extractEmail(token));
        LocalDateTime time = jwtService.extractExpiration(token).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        body.put("expirationDate", DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss").format(time));
        body.put("isValid", jwtService.isTokenValid(token, id));
        // body.put("type", jwtService.isTokenValid(token, id));

        return new ResponseEntity<Map<String, Object>>(body, HttpStatus.OK);
    }

}
