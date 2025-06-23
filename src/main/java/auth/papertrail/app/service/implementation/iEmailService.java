package auth.papertrail.app.service.implementation;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import auth.papertrail.app.service.interfase.EmailService;

@Service
public class iEmailService implements EmailService {

    private JavaMailSender mailSender;

    private ResourceLoader resourceLoader;

    @Value("${auth-service.email.from}")
    private String from;

    @Value("${auth-service.email.subject}")
    private String subject;

    @Value("${auth-service.email.body}")
    private String body;

    @Autowired
    public iEmailService(JavaMailSender mailSender, ResourceLoader resourceLoader) {
        this.mailSender = mailSender;
        this.resourceLoader = resourceLoader;
    }

    public void sendVerificationEmail(String to) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        System.out.println(from);
        System.out.println(to);
        System.out.println(String.format(getVerificationEmailAsString(), to, "https://localhost:8080/actuator/health"));
        message.setText(String.format(getVerificationEmailAsString(), to, "https://localhost:8080/actuator/health"));
        // These emails are being received in SPAM folder
        try {
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    private String getVerificationEmailAsString() {
        Resource resource = resourceLoader.getResource(body);
        String body = null;
        try (InputStream input = resource.getInputStream()) {
            body = new String(input.readAllBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return body;
    }

}
