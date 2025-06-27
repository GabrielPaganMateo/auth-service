package auth.papertrail.app.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.auth0.jwt.algorithms.Algorithm;

@Configuration
public class SigningAlgorithmConfig {

    @Value("auth-service.algorithm.secret")
    String secret; // Must stored in AWS secrets manager

    @Bean
    Algorithm getAlgorithm() {
        return Algorithm.HMAC256(secret);
    }

}
