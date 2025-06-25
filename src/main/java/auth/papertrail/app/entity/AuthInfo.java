package auth.papertrail.app.entity;

import auth.papertrail.app.enumerator.UserStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;

@Entity
public class AuthInfo {

    @Id
    @MapsId
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id")
    private EndUser user;
    private String password;
    private UserStatus userStatus;

    public AuthInfo() {}

    public AuthInfo(EndUser user) {
        this.user = user;
    }

    public AuthInfo(EndUser user, UserStatus userStatus) {
        this.user = user;
        this.userStatus = userStatus;
    }

    public EndUser getUser() {
        return user;
    }

    public void setUser(EndUser user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }
    
}
