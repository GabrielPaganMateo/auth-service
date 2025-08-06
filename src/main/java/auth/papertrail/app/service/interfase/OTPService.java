package auth.papertrail.app.service.interfase;

import auth.papertrail.app.entity.EndUser;
import auth.papertrail.app.entity.OneTimePasscode;

public interface OTPService {
    
    public int generateOTP(EndUser user);

    public OneTimePasscode getUserOTP(EndUser user);

    public boolean isExpiredOTP(OneTimePasscode otp);

    public int updateOTP(OneTimePasscode otp);

    public void sendNewOTP(EndUser user);

}
