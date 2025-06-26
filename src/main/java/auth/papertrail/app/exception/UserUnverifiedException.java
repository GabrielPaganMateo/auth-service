package auth.papertrail.app.exception;

import java.util.Map;

import auth.papertrail.app.enumerator.ExceptionCode;

public class UserUnverifiedException extends AuthException {

    private String email;

    public UserUnverifiedException(String email) {
        super(ExceptionCode.USER_UNVERIFIED.getCode(), ExceptionCode.USER_UNVERIFIED.getMessage(), Map.of("email", email));
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
