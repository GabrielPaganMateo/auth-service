package user.gateway.auth_service.Enums;

public enum VerifiedStatusEnum {
    
    UNVERIFIED("Unverified"),
    VERIFIED("Verified");

    private final String verifiedStatus;

    private VerifiedStatusEnum(String verifiedStatus) {
        this.verifiedStatus = verifiedStatus;
    }

    public String getVerifiedStatus() {
        return verifiedStatus;
    }

}