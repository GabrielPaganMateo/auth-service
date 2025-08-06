package auth.papertrail.app.bean;

public class VerificationMail {

    private String template;

    public VerificationMail(String template) {
        this.template = template;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String formatVerificationTemplate(String redirect) {
        return String.format(template, redirect);
    }

    public String getTemplateWithToken(int token) {
        return String.format(template, token);
    }

}
