package user.gateway.auth_service.Enums;

public enum TokenTypeEnum {
    
    USER_LOGIN("USER_LOGIN", 0),
    EMAIL_VERIFICATION("EMAIL_VERIFICATION", 1);

    private final String typeName;
    private final int typeCode;

    private TokenTypeEnum(String typeName, int typeCode) {
        this.typeName = typeName;
        this.typeCode = typeCode;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getTypeCode() {
        return typeCode;
    }
}
