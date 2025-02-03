package user.gateway.auth_service.Feigns;

import java.util.Map;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import user.gateway.auth_service.Entities.User;

@FeignClient(name = "users", url = "#{@usersFeignClientUrl}")
public interface UserFeign {

    @GetMapping("/user")
    ResponseEntity<User> getUser(@RequestParam("id") String id);

    @PostMapping("/user")
    ResponseEntity<?> registerUser(@RequestBody User user);

    @PostMapping("/user/email")
    Map<String, Boolean> isUserRegistered(@RequestBody User user);
    
}
