package auth.papertrail.app.service.interfase;

import auth.papertrail.app.entity.EndUser;

public interface EmailService {

    public void sendVerificationEmail(EndUser user, String token);

}
