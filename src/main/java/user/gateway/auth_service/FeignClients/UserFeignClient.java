package user.gateway.auth_service.FeignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import user.gateway.auth_service.Entities.User;

@FeignClient(name = "UserFeignClient", url = "${dynamodb.api.url}")
public interface UserFeignClient {

    @PostMapping("/user")
    User postUser(@RequestBody User user);
    
}
