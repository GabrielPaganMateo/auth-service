package user.gateway.auth_service.Configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfigurations {

    @Bean
    public String usersFeignClientUrl(AwsProperties awsProperties) {
        return awsProperties.getApiGateway().getDynamodb().getUsers();
    }

}
