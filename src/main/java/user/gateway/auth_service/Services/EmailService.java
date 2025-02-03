package user.gateway.auth_service.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import user.gateway.auth_service.Constants.EmailMessages;
import user.gateway.auth_service.Constants.UrlPaths;

@Service
public class EmailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Value("${server.host.address}")
    private String address;

    public String sendEmail(String[] emailAndJwt) {
        String email = emailAndJwt[0];
        String jwt = emailAndJwt[1];
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("dev.athmb@gmail.com");
        message.setTo(email);
        message.setSubject("Verify email (Auth-Service)");
        message.setText(String.format(EmailMessages.VERIFY_LINK, address, UrlPaths.VERIFY, jwt));
        javaMailSender.send(message);
        return jwt;
    }

}
