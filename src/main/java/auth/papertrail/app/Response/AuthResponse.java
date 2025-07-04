package auth.papertrail.app.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import auth.papertrail.app.enumerator.ResponseCode;

@JsonPropertyOrder({ "code", "message", "details" })
public class AuthResponse {

    private String code;
    private String message;
    private Map<String, String> details;

    public AuthResponse(ResponseCode code, Map<String, String> details) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.details = details;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public void setDetails(Map<String, String> details) {
        this.details = details;
    }
    
}
