package auth.papertrail.app.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import auth.papertrail.app.bean.VerificationMail;
import auth.papertrail.app.entity.EndUser;
import auth.papertrail.app.service.interfase.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class iEmailService implements EmailService {

    private final JavaMailSender mailSender;

    private final VerificationMail verificationMail;

    @Value("${auth-service.email.verification.from}")
    private String from;

    @Value("${auth-service.email.verification.subject}")
    private String subject;

    @Autowired
    public iEmailService(JavaMailSender mailSender, VerificationMail verificationMail) {
        this.mailSender = mailSender;
        // this.resourceLoader = resourceLoader;
        this.verificationMail = verificationMail;
    }

    public void sendVerificationEmail(EndUser user, int code) {
        new Thread(() -> {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);
                helper.setFrom(from);
                helper.setTo(user.getEmail());
                helper.setSubject(subject);
                helper.setText(verificationMail.getTemplateWithToken(code), true);
                mailSender.send(message);
            } catch (MessagingException | MailException e) {
                e.printStackTrace();
            }
        }).start();
    }

}
