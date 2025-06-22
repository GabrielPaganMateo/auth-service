package auth.papertrail.app.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import auth.papertrail.app.service.interfase.EmailService;

@Service
public class iEmailService implements EmailService {

    private JavaMailSender mailSender;

    @Autowired
    public iEmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String to) {
        System.out.println("sending..");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setFrom("gabriel.pagan.evertec@gmail.com");
        message.setSubject("test"); 
        message.setText("hello");
        // These emails are being received in SPAM folder
        try {
        mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
