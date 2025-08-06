package auth.papertrail.app.configuration;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import auth.papertrail.app.bean.VerificationMail;

@Configuration
public class MailTemplateConfig {

    @Value("${auth-service.email.verification.file}")
    private String file;

    // @Value("${auth-service.email.verification.href}")
    // private String href;

    @Bean    
    public VerificationMail verificationMail() {
        ResourceLoader resourceLoader = new DefaultResourceLoader();
        Resource resource = resourceLoader.getResource(file);
        String template = null;
        try (InputStream input = resource.getInputStream()) {
            template = new String(input.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new VerificationMail(template);
    }
}
