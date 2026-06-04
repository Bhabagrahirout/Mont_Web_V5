package com.apmosys.framework;

import java.util.Properties;

/**
 * BrowserConfig — owns all browser launch settings.
 *
 * Previously scattered across Framework static fields and the giant
 * StartBrowser() method.  Now a plain value object that describes HOW a
 * browser should be started.  The actual driver creation stays in
 * BrowserFactory (Step 2 of refactoring).
 *
 * Usage:
 *   BrowserConfig cfg = BrowserConfig.fromProperties(props);
 *   WebDriver driver = BrowserFactory.create(cfg);
 */
public class BrowserConfig {

    // ── Browser identity ─────────────────────────────────────────────────────
    private String browserName;   // Chrome | FF | Edge | IE | Safari | sikuli
    private boolean headless;

    // ── Page load behaviour ──────────────────────────────────────────────────
    private int    pageLoadTimeoutSeconds;
    private String fastPageLoad;   // Y | None | (empty = NORMAL)
    private boolean pageStability; // --disable-dev-shm-usage

    // ── Download behaviour ───────────────────────────────────────────────────
    private boolean defaultDownload;  // true = open in browser; false = save to disk

    // ── Locator debugging ────────────────────────────────────────────────────
    private boolean showLocator;

    // ── Firefox profile ──────────────────────────────────────────────────────
    private String ffProfilePath;

    // ── Custom JAR plugin ────────────────────────────────────────────────────
    private String customJarPath;
    private String customClassName;

    // ── Browser state — tracks whether a window is already open ──────────────
    // (was Framework.browsersts)
    private boolean browserOpen = false;
    private String  lastBrowserName = "";

    // ── Startup gate ─────────────────────────────────────────────────────────
    private boolean startBrowserEnabled;  // startBrowserStatus in props

    // ── Private constructor — use factory ────────────────────────────────────
    private BrowserConfig() {}

    /**
     * Build a BrowserConfig from the framework properties file.
     * browserName is read from Main_Controller_Web.xlsx at runtime,
     * so it must be set separately via setBrowserName().
     */
    public static BrowserConfig fromProperties(Properties props, String projectDir) {
        BrowserConfig cfg = new BrowserConfig();

        cfg.headless = Boolean.parseBoolean(
                props.getProperty("headless", "false"));

        cfg.pageLoadTimeoutSeconds = parseIntSafe(
                props.getProperty("pageLoadTime", "90"), 90);

        cfg.fastPageLoad    = props.getProperty("fastPageLoad", "N");
        cfg.pageStability   = "Y".equalsIgnoreCase(props.getProperty("pageStability", "N"));
        cfg.defaultDownload = "Y".equalsIgnoreCase(props.getProperty("defaultDownload", "Y"));
        cfg.showLocator     = "Y".equalsIgnoreCase(props.getProperty("showLocator", "N"));

        cfg.ffProfilePath = props.getProperty(
                "ffProfilePath",
                "/home/apmosys/.mozilla/firefox/s3v60wo7.default-release");

        String rawJarPath = props.getProperty("customJarPath", "");
        cfg.customJarPath = rawJarPath.isBlank()
                ? ""
                : projectDir + java.io.File.separator + rawJarPath;

        cfg.customClassName = props.getProperty("customClassName", "");

        cfg.startBrowserEnabled = !"N".equalsIgnoreCase(
                props.getProperty("startBrowserStatus", "Y"));

        return cfg;
    }

    // ── Browser state management ──────────────────────────────────────────────

    /** Called by BrowserFactory after a driver is successfully created. */
    public void markBrowserOpen(String browserName) {
        this.browserOpen = true;
        this.lastBrowserName = browserName;
    }

    /** Called by BrowserFactory after driver.quit(). */
    public void markBrowserClosed() {
        this.browserOpen = false;
    }

    /**
     * Returns true if we need to close the current browser and open a new one.
     * Mirrors the old Framework.browsersts / oldbrowser logic.
     */
    public boolean shouldReopenBrowser(String requestedBrowser) {
        if (!browserOpen) return false;
        return requestedBrowser.toUpperCase().contains("NEW")
                || !lastBrowserName.equalsIgnoreCase(requestedBrowser);
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public String  getBrowserName()              { return browserName; }
    public boolean isHeadless()                  { return headless; }
    public int     getPageLoadTimeoutSeconds()   { return pageLoadTimeoutSeconds; }
    public String  getFastPageLoad()             { return fastPageLoad; }
    public boolean isPageStabilityEnabled()      { return pageStability; }
    public boolean isDefaultDownload()           { return defaultDownload; }
    public boolean isShowLocator()               { return showLocator; }
    public String  getFfProfilePath()            { return ffProfilePath; }
    public String  getCustomJarPath()            { return customJarPath; }
    public String  getCustomClassName()          { return customClassName; }
    public boolean isBrowserOpen()               { return browserOpen; }
    public String  getLastBrowserName()          { return lastBrowserName; }
    public boolean isStartBrowserEnabled()       { return startBrowserEnabled; }

    // ── Setters ───────────────────────────────────────────────────────────────

    public void setBrowserName(String browserName) { this.browserName = browserName; }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private static int parseIntSafe(String value, int fallback) {
        try {
            return Integer.parseInt(value.trim());
        } catch (NumberFormatException e) {
            return fallback;
        }
    }
}
