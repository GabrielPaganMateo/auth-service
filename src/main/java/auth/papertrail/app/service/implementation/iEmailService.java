package auth.papertrail.app.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import auth.papertrail.app.bean.VerificationMail;
import auth.papertrail.app.service.interfase.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class iEmailService implements EmailService {

    private JavaMailSender mailSender;

    // private ResourceLoader resourceLoader;

    private VerificationMail verificationMail;

    @Value("${auth-service.email.verification.from}")
    private String from;

    @Value("${auth-service.email.verification.subject}")
    private String subject;

    // @Value("${auth-service.email.body}")
    // private String body;

    @Autowired
    public iEmailService(JavaMailSender mailSender, VerificationMail verificationMail) {
        this.mailSender = mailSender;
        // this.resourceLoader = resourceLoader;
        this.verificationMail = verificationMail;
    }

    public void sendVerificationEmail(String to) {
        try {
            String text = verificationMail.formatVerificationTemplate(to, "https://localhost:8080/actuator/health");
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text, true);
            mailSender.send(message);
        } catch (MessagingException | MailException e) {
            e.printStackTrace();
        }
    }

    // private String getVerificationEmailTemplate() {
    //     Resource resource = resourceLoader.getResource(body);
    //     String body = null;
    //     try (InputStream input = resource.getInputStream()) {
    //         body = new String(input.readAllBytes());
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return body;
    // }

}
