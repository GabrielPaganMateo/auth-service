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

    public String formatVerificationTemplate(String to, String redirect) {
        return String.format(template, to, redirect);
    }

}
