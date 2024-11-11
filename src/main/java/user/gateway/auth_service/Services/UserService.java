package user.gateway.auth_service.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import user.gateway.auth_service.Entities.User;
import user.gateway.auth_service.FeignClients.UserFeignClient;
import user.gateway.auth_service.Requests.RegisterRequest;

@Service
public class UserService {

    @Autowired
    private UserFeignClient userFeignClient;

    public User getUser(String id) {
        return userFeignClient.getUser(id);
    }

    public User postUser(RegisterRequest registerRequest) {
        return userFeignClient.postUser(userDataTransfer(registerRequest));
    }

    private User userDataTransfer(RegisterRequest registerRequest) {
        User newUser = new User();
        newUser.setPassword(registerRequest.getPassword());
        return newUser;
    }

}
