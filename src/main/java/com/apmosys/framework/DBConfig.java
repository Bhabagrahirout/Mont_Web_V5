package com.apmosys.framework;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Properties;

/**
 * DBConfig — owns all database connectivity state.
 *
 * Previously scattered as static fields on Framework, now in one place.
 * Holds the live connection and exposes a clean connect/close lifecycle.
 *
 * Usage:
 *   DBConfig db = DBConfig.fromProperties(props);
 *   db.connect();
 *   ...use db.getConnection()...
 *   db.close();
 */
public class DBConfig {

    // ── Connection coordinates ────────────────────────────────────────────────
    private String url;
    private String username;
    private String password;   // decoded at construction time
    private String driver;

    // ── OTP DB (separate data-source) ────────────────────────────────────────
    private String otpJdbcDriver;
    private String otpJdbcUrl;
    private String otpJdbcUsername;
    private String otpJdbcPassword;

    // ── Live connection ───────────────────────────────────────────────────────
    private java.sql.Connection connection;

    // ── Private constructor — use factory ────────────────────────────────────
    private DBConfig() {}

    /**
     * Build a DBConfig from the framework properties file.
     * Decodes the Base64-encoded password automatically.
     */
    public static DBConfig fromProperties(Properties props) {
        DBConfig cfg = new DBConfig();

        cfg.driver   = props.getProperty("jdbc.driver");
        cfg.url      = props.getProperty("jdbc.url");
        cfg.username = props.getProperty("jdbc.username");

        // Password is stored Base64-encoded in the properties file
        String encodedPassword = props.getProperty("jdbc.password", "");
        cfg.password = decodeBase64(encodedPassword);

        cfg.otpJdbcDriver   = props.getProperty("otp.jdbc.driver");
        cfg.otpJdbcUrl      = props.getProperty("otp.jdbc.url");
        cfg.otpJdbcUsername = props.getProperty("otp.jdbc.username");
        cfg.otpJdbcPassword = props.getProperty("otp.jdbc.password");

        return cfg;
    }

    /**
     * Opens the JDBC connection.  Logs success/failure clearly.
     * Call once at framework startup; reuse getConnection() throughout.
     */
    public void connect() {
        try {
            if (driver != null) {
                Class.forName(driver);
            }
            System.out.println(" [DB]  Connecting to: " + url);
            connection = DriverManager.getConnection(url, username, password);
            System.out.println(" [DB]  CONNECTION : Established [✓]");
        } catch (ClassNotFoundException e) {
            System.err.println(" [DB]  DRIVER NOT FOUND: " + driver);
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println(" [DB]  CONNECTION FAILED: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Closes the connection gracefully.  Safe to call even if never opened.
     */
    public void close() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println(" [DB]  CONNECTION : Closed [✓]");
            } catch (SQLException e) {
                System.err.println(" [DB]  Error closing connection: " + e.getMessage());
            } finally {
                connection = null;
            }
        }
    }

    /** @return true if the connection is open and usable. */
    public boolean isConnected() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public java.sql.Connection getConnection() { return connection; }
    public String getUrl()         { return url; }
    public String getUsername()    { return username; }
    public String getOtpJdbcUrl()  { return otpJdbcUrl; }
    public String getOtpJdbcUsername() { return otpJdbcUsername; }
    public String getOtpJdbcPassword() { return otpJdbcPassword; }
    public String getOtpJdbcDriver()   { return otpJdbcDriver; }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static String decodeBase64(String encoded) {
        if (encoded == null || encoded.isBlank()) return "";
        try {
            return new String(Base64.getDecoder().decode(encoded.trim()));
        } catch (IllegalArgumentException e) {
            // Not Base64 — treat as plain text (handles local dev overrides)
            return encoded;
        }
    }
}
