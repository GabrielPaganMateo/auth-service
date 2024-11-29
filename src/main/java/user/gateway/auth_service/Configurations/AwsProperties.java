package user.gateway.auth_service.Configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@Configuration
@PropertySource("classpath:aws.properties")
@ConfigurationProperties(prefix = "aws")
public class AwsProperties {

    private Apigateway apigateway;

    public Apigateway getApiGateway() {
        return apigateway;
    }

    public void setApiGateway(Apigateway apigateway) {
        this.apigateway = apigateway;
    }

    public static class Apigateway {

        private Dynamodb dynamodb;

        public Dynamodb getDynamodb() {
            return dynamodb;
        }

        public void setDynamodb(Dynamodb dynamodb) {
            this.dynamodb = dynamodb;
        }

        public static class Dynamodb {
             
            private String users;

            public String getUsers() {
                return users;
            }

            public void setUsers(String users) {
                this.users = users;
            }
        
        }
    }


}
