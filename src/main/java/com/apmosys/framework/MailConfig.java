package com.apmosys.framework;

import java.util.Properties;

/**
 * MailConfig — owns all email/alert configuration.
 *
 * Previously spread across Framework static fields (host, port, mailFrom,
 * password, mailTo, mailCc, subject, mailAlert, etc.).  Now a single,
 * self-contained object that you can pass to any mail-sending utility.
 *
 * Usage:
 *   MailConfig mail = MailConfig.fromProperties(props);
 *   if (mail.isAlertEnabled()) { FetchMail.send(mail, message); }
 */
public class MailConfig {

    // ── SMTP server ──────────────────────────────────────────────────────────
    private String host;
    private String port;
    private String authId;

    // ── Credentials ──────────────────────────────────────────────────────────
    private String mailFrom;
    private String password;   // NOTE: move to env-var in Step 5

    // ── Recipients ───────────────────────────────────────────────────────────
    private String mailTo;
    private String mailCc;

    // ── Content ──────────────────────────────────────────────────────────────
    private String subject;
    private String message;
    private String errorMsg;
    private String extraMsg;

    // ── Feature flags ────────────────────────────────────────────────────────
    private boolean alertEnabled;   // mailAlert=Y in properties

    // ── Private constructor — use factory ────────────────────────────────────
    private MailConfig() {}

    /**
     * Build a MailConfig from the framework properties file.
     */
    public static MailConfig fromProperties(Properties props) {
        MailConfig cfg = new MailConfig();

        cfg.host     = props.getProperty("host", "");
        cfg.port     = props.getProperty("port", "587");
        cfg.authId   = props.getProperty("authId", "");
        cfg.mailFrom = props.getProperty("mailFrom", "");
        cfg.password = props.getProperty("password", "");  // Step 5: move to env
        cfg.mailTo   = props.getProperty("mailTo", "");
        cfg.mailCc   = props.getProperty("mailCc", "");
        cfg.subject  = props.getProperty("subject", "");
        cfg.message  = props.getProperty("message", "");
        cfg.errorMsg = props.getProperty("errorMsg", "");
        cfg.extraMsg = props.getProperty("extraMsg", "");

        cfg.alertEnabled = "Y".equalsIgnoreCase(props.getProperty("mailAlert", "N"));

        return cfg;
    }

    /** @return true if alert emails should be sent on failure. */
    public boolean isAlertEnabled() { return alertEnabled; }

    // ── Getters ───────────────────────────────────────────────────────────────

    public String getHost()     { return host; }
    public String getPort()     { return port; }
    public String getAuthId()   { return authId; }
    public String getMailFrom() { return mailFrom; }
    public String getPassword() { return password; }
    public String getMailTo()   { return mailTo; }
    public String getMailCc()   { return mailCc; }
    public String getSubject()  { return subject; }
    public String getMessage()  { return message; }
    public String getErrorMsg() { return errorMsg; }
    public String getExtraMsg() { return extraMsg; }

    // ── Setters (allow runtime override for per-run subjects etc.) ────────────

    public void setSubject(String subject)   { this.subject = subject; }
    public void setMessage(String message)   { this.message = message; }
    public void setErrorMsg(String errorMsg) { this.errorMsg = errorMsg; }
    public void setExtraMsg(String extraMsg) { this.extraMsg = extraMsg; }

    @Override
    public String toString() {
        return "MailConfig{from=" + mailFrom + ", to=" + mailTo
                + ", alertEnabled=" + alertEnabled + "}";
    }
}
